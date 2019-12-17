SpringBoot中jpa加载类图
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/1.png)
##### 来看一下如何加载Hibernate jpa:
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/2.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/3.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/4.png)
##### 直接看出来有哪些配置
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/5.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/6.png)
##### 加载Hibernate的过程：
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/7.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/8.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/9.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/10.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/11.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/12.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/13.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/14.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/15.png)
##### 启动成功之后通过@PersistenceUnit和@@PersistenceContext可以获得想要的EntityManager
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/16.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/hibernate_bootstrap/17.png)