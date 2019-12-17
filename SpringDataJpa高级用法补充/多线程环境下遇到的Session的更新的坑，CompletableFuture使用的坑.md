### 发生问题的代码如下：

````
1. spring.jpa.open-in-view=true   
2. Controller里面的代码如下：(代码示例一)
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final Executor executor;

    @GetMapping("test/saveUser")
    public String testSaveUser(Long name) throws InterruptedException {
        CompletableFuture<Void>cf = CompletableFuture.runAsync(() -> {
            User user = userRepository.findById(1L).get();
            user.setName(RandomUtils.nextInt(1,100000)+ "aaaaaaaaaaaa"+name);
            userRepository.save(user);
            
            user.setName(RandomUtils.nextInt(1,100000)+ "bbbbbbbbbbb"+name);
           userRepository.save(user);
        }, executor);
        cf.isDone();
        return "Success";
    }
````
#### 问题：
1. 你会发现，程序执行的是什么现象都没有，也没有异常，也没有报错，但是数据库里面bbbbb也没有保存到数据库里面。数据库里面永远是`aaaaaaaaaaaa`

#### 稍加调整，如下代码是执行没有问题的：
````
1. controller里面稍做调整：(代码示例二)
    @GetMapping("test/saveUser")
    public String testSaveUser(Long name) throws InterruptedException {
        User user = userRepository.findById(1L).get();
       // user.getVersion()==0,这个时候version是数据里面的
        user.setName(RandomUtils.nextInt(1, 100000) + "aaaaaaaaaaaa" + name);
        userRepository.save(user);
        //这个时候user对象里面的version已经是最新的了，发生了变化 version=1
        user.setName(RandomUtils.nextInt(1, 100000) + "bbbbbbbbbbb" + name);
        userRepository.save(user);
        //这个时候version已经变成user.getVersion()==2了
        return "Success";
    }
//为什么这个是没问题的呢？因为open in view 导致user一直在同一个PersistenceContext里面，而session会自己改PersistenceContext里面user的版本号；


````
#### 代码示例一种存在的几个问题：
1. 首先CompletableFuture在使用的时候一定要加异常处理，否则发生异常的时候，日志没有任何体现。
2. 由于controller的方法上面没有加事务，所以导致user对象跨越了两个PersistenceContext，既两个Session，而第一次都是同一个User对象的引用地址，所以当第一个成功之后，第二次save的时候还是老的对象引用地址的值，所以version没有发生变化，就会第二次没有保存成功。
#### 所以正确的改动方法如下：
1. 第一步先把异常信息打印出来:

```` 
@GetMapping("test/saveUser")
    public String testSaveUser(Long name) throws InterruptedException {
        CompletableFuture<Void>cf = CompletableFuture.runAsync(() -> {
            User user = userRepository.findById(1L).get();
            user.setName(RandomUtils.nextInt(1,100000)+ "aaaaaaaaaaaa"+name);
            userRepository.save(user);
            
            user.setName(RandomUtils.nextInt(1,100000)+ "bbbbbbbbbbb"+name);
           userRepository.save(user);
        }, executor).exceptionally(e->{
//这里我们就知道发生了什么了
            log.info("EEEEEEEEEEEEEEEEEEEEE"+e.getMessage());
            log.error(e.getMessage(),e);
            return null;
        });
        cf.isDone();
        return "Success";
    }
````
2. 第二步：第一种改法加上version版本号即可
````
@GetMapping("test/saveUser")
    public String testSaveUser(Long name) throws InterruptedException {
        CompletableFuture<Void>cf = CompletableFuture.runAsync(() -> {
            User user = userRepository.findById(1L).get();
            user.setName(RandomUtils.nextInt(1,100000)+ "aaaaaaaaaaaa"+name);
            userRepository.save(user);
            //每save一次版本号+1即可，但不是推荐做法；
            user.setVersion(user.getVersion()+1);

            user.setName(RandomUtils.nextInt(1,100000)+ "bbbbbbbbbbb"+name);
           userRepository.save(user);
        }, executor).exceptionally(e->{
//这里我们就知道发生了什么了
            log.info("EEEEEEEEEEEEEEEEEEEEE"+e.getMessage());
            log.error(e.getMessage(),e);
            return null;
        });
        cf.isDone();
        return "Success";
    }
````
2. 第二步：第二种改法使用save之后的对象；
````
@GetMapping("test/saveUser")
    public String testSaveUser(Long name) throws InterruptedException {
        CompletableFuture<Void>cf = CompletableFuture.runAsync(() -> {
            User user = userRepository.findById(1L).get();
            user.setName(RandomUtils.nextInt(1,100000)+ "aaaaaaaaaaaa"+name);
//使用save成功之后的对象即可
            user = userRepository.save(user);

            user.setName(RandomUtils.nextInt(1,100000)+ "bbbbbbbbbbb"+name);
           userRepository.save(user);
        }, executor).exceptionally(e->{
//这里我们就知道发生了什么了
            log.info("EEEEEEEEEEEEEEEEEEEEE"+e.getMessage());
            log.error(e.getMessage(),e);
            return null;
        });
        cf.isDone();
        return "Success";
    }
````







