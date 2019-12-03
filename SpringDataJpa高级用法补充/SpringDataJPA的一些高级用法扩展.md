本场 Chat 比较适合对 Spring Data JPA 和 Hibernate 有一定认识的同学，特别是看过老师的书的同学。

### 一、JPA 关注的 Hibernate 在 Application 的配置有哪些?

#### 1.1 SQL 和参数的打印问题
application.properties 配置方法如下：

```
//sql标准输出
spring.jpa.show-sql=true
//format一下sql进行输出
spring.jpa.properties.hibernate.format_sql=true
//显示出来jpa insert的注释信息
spring.jpa.properties.hibernate.use_sql_comments=true
```
控制台输出的格式如下：

```
Hibernate: 
    /* insert com.alo7.notification.message.core.domain.MessageRequestTelephone
        */ 
        insert 
        into
            message_request_telephone
            (created_at, updated_at, version, created_by, updated_by, message_request_id, nation_code, retry_times, serial_number, status, status_remark, telephone, telephone_original, template_detail_id) 
        values
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
```
我们想显示出来参数怎么办?


原理很简单，我们只要找到参数绑定的地方把日志级别开启即可，详细看 BasicBinder 类。
```
//hibernate所有的操作都是PreparedStatement
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
//开启参数效果如下：
[21304] 2018-07-22 11:48:13.490 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [1] as [TIMESTAMP] - [2018-07-22T03:48:13.422Z]
[21304] 2018-07-22 11:48:13.493 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [2] as [TIMESTAMP] - [2018-07-22T03:48:13.422Z]
[21304] 2018-07-22 11:48:13.495 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [3] as [INTEGER] - [0]
[21304] 2018-07-22 11:48:13.500 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [4] as [VARCHAR] - [-1]
[21304] 2018-07-22 11:48:13.501 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [5] as [VARCHAR] - [-1]
[21304] 2018-07-22 11:48:13.502 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[53] - binding parameter [6] as [VARCHAR] - [null]
[21304] 2018-07-22 11:48:13.506 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[53] - binding parameter [7] as [INTEGER] - [null]
[21304] 2018-07-22 11:48:13.509 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[53] - binding parameter [9] as [VARCHAR] - [null]
[21304] 2018-07-22 11:48:13.510 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [10] as [VARCHAR] - [MESSAGE_TEST]
[21304] 2018-07-22 11:48:13.510 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [11] as [VARCHAR] - [自动化压测-JACK, 3344]
[21304] 2018-07-22 11:48:13.511 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - TRACE [http-nio-8080-exec-1] org.hibernate.type.descriptor.sql.BasicBinder.bind[65] - binding parameter [12] as [VARCHAR] - [59ba9069-2f80-4f81-839c-3c406a031f8a]
[21304] 2018-07-22 11:48:13.605 - [notification-api,04c9987e69d40628,04c9987e69d40628,false] - DEBUG [http-nio-8080-exec-1] org.hibernate.SQL.logStatement[92] - 
//如果需要更详细的日志，顺理往上面推就行了，如下：
\#logging.level.org.hibernate.SQL=DEBUG
\#logging.level.org.hibernate.type.descriptor.sql=trace
```
有的时候我们需要知道连接池的情况：

```
//hikari连接池日志
logging.level.com.zaxxer.hikari=DEBUG
//日志效果如下:
[21424] 2018-07-22 12:03:07.092 - [notification-api,0,0,false] - DEBUG [NotificationApiHikariCPPool housekeeper] com.zaxxer.hikari.pool.HikariPool.logPoolState[404] - NotificationApiHikariCPPool - Pool stats (total=20, active=0, idle=20, waiting=0)
```
#### 1.2 关注的 Hibernate 配置有哪些？
除了上述的 Hibernate 的配置，我们还需要关注的设置有哪些呢？

```
//根据@Entity创建数据的策略,可以根据Entity自动生成table
spring.jpa.hibernate.ddl-auto=update
//dialect是什么，如果我们用H2做Test需要注意
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
//是否生成统计信息，结合我们之前讲的prometheus，是否生成db级别的statistics
spring.jpa.properties.hibernate.generate_statistics=true
    //prometheus格式对应如下：
    # TYPE jdbc_connections_min gauge
    jdbc_connections_min{application="notification-api",cloud_client_host="192.168.120.162",name="dataSource",} 20.0
    jdbc_connections_max{application="notification-api",cloud_client_host="192.168.120.162",name="dataSource",} 20.0
//column和db 字段映射的name strategy
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

#### 1.3 如何统观全局查找这些配置

常见的是以上几个，当然还有很多我们不常见的,这个时候就需要我们去看 AvailableSettings 的源码了，纵观全局有哪些参数我们可以设置，做到心中有数部分源码如下:

```
package org.hibernate.cfg;
public interface AvailableSettings {
	String DEFAULT_SCHEMA = "hibernate.default_schema";
	String DEFAULT_CACHE_CONCURRENCY_STRATEGY = "hibernate.cache.default_cache_concurrency_strategy";
	//sql关键字的自动处理策略
	String KEYWORD_AUTO_QUOTING_ENABLED = "hibernate.auto_quote_keyword";
	String SESSION_FACTORY_NAME = "hibernate.session_factory_name";
	String MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
	String JDBC_TIME_ZONE = "hibernate.jdbc.time_zone";
	String HBM2DLL_CREATE_NAMESPACES = "hibernate.hbm2dll.create_namespaces";
    ......
}
```
太多了就不一一看了，如果我们熟练掌握 Hibernate 和 JPA 还是有必要把这个类看一下的，最起码知道 Hibernate 和 JPA 的方向在哪里，以至于我们查资料的时候方向不会走错。


### 二、Spring Data JPA 中 N+1 条 SQL 的实战使用

当我们使用 @ManyToOne、@OneToMany、@ManyToMany 的时候，会有 loazy 之后出现 N+1 条 SQL 的问题，我们有如下解决方案：

```
@Entity
@Table(name = "message_template")
public class MessageTemplate {
    @OneToMany(mappedBy = "messageTemplate")
    public List<MessageTemplateDetail> getMessageTemplateDetailList() {
        return messageTemplateDetailList;
    }
    ......
}
@Entity
@Table(name = "message_template_detail")
public class MessageTemplateDetail{
    @ManyToOne
    @JoinColumn(name = "message_template_id", referencedColumnName = "id", insertable = false, updatable = false)
    public MessageTemplate getMessageTemplate() {
        return messageTemplate;
    }
    ......
}
```
我们以上面的 @OneToMany 为例，作如下查询：
```
MessageTemplate findByTemplateCode(String templateCode);
```
会分别打印如下两个 SQL：

```
Hibernate: 
    select
        messagetem0_.id as id1_7_,
        messagetem0_.title as title12_7_ 
    from
        message_template messagetem0_ 
    where
        messagetem0_.template_code=?
Hibernate: 
    select
        messagetem0_.message_template_id as message_8_8_0_,
        messagetem0_.id as id1_8_0_,
        messagetem0_.message_template_id as message_8_8_1_,
        messagetem0_.message_vendor_code as message_9_8_1_,
        messagetem0_.nation_code as nation_10_8_1_, 
    from
        message_template_detail messagetem0_ 
    where
        messagetem0_.message_template_id=?
```
我们通过如下几种方式来解决。

#### 2.1 @EntityGraph 的使用

```
//1)Entity上面添加@NamedEntityGraph注解
@Entity
@Table(name = "message_template")
@NamedEntityGraph(name = "MessageTemplate.messageTemplateDetailList",
        attributeNodes = @NamedAttributeNode("messageTemplateDetailList"))
public class MessageTemplate {
    @OneToMany(mappedBy = "messageTemplate")
    public List<MessageTemplateDetail> getMessageTemplateDetailList() {
        return messageTemplateDetailList;
    }
    ......
}
//2）查询方法上添加@EntityGraph注解
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    @EntityGraph(value = "MessageTemplate.messageTemplateDetailList", type = EntityGraph.EntityGraphType.LOAD)
    MessageTemplate findByTemplateCode(String templateCode);
}
```
我们再次方法其方法打印的 SQL 如下：

```
Hibernate: 
    select
        messagetem0_.id as id1_7_0_,
        messagetem1_.id as id1_8_1_,
        messagetem0_.template_code as templat10_7_0_,
        messagetem0_.title as title12_7_0_,
        messagetem1_.message_template_id as message_8_8_1_,
        messagetem1_.message_template_id as message_8_8_0__,
        messagetem1_.id as id1_8_0__ 
    from
        message_template messagetem0_ 
    left outer join
        message_template_detail messagetem1_ 
            on messagetem0_.id=messagetem1_.message_template_id 
    where
        messagetem0_.template_code=?
```
#### 2.2 @Query 或者 JpaSpecificationExecutor 扩展 left join 使用

@Query 使用：

```
    @Query("Select t from MessageTemplate t,MessageTemplateDetail d where t.id=d.templateId and t.templateCode=:templateCode ")
    MessageTemplate findByTemplateCode(@Param("templateCode") String templateCode);
```

JpaSpecificationExecutor 扩展，我们直接引用书中的例子，使用root.join 做联表查询即可。

```
 public Page<UserInfoEntity> findByCondition(UserInfoRequest userParam,Pageable pageable){
      return userRepository.findAll((root, query, cb) -> {
         List<Predicate> predicates = new ArrayList<Predicate>();
         if (StringUtils.isNoneBlank(userParam.getFirstName())){
            //liked的查询条件
            predicates.add(cb.like(root.get("firstName"),"%"+userParam.getFirstName()+"%"));
         }
         if (StringUtils.isNotBlank(userParam.getAddressCity())) {
            //联表查询，利用root的join方法，根据关联关系表里面的字段进行查询。
            predicates.add(cb.equal(root.join("addressEntityList").get("addressCity"), userParam.getAddressCity()));
         }
         return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
      }, pageable);
   }
}
```
#### 2.3 View 也不外乎一种手段

还有一种简单的解决方案就是，直接视图，直接把 TableView 配置成 @Entity 即可。

- 缺点：SQL 维护在数据那边，需要做好 SQL 的修改记录；
- 优点：调整 SQL 不需要发版，比较适合做报表展示。

当然了如果大家有更好的方案，也欢迎留言呀。

### 三、@Entity 中 Lazy Load 所遇到的坑

通常情况下，当我们使用 `@OneToOne(fetch = FetchType.LAZY)  FetchType.LAZY` 的时候都是延迟加载没有问题的，但有时候当我们引用第三方库的时候 LAZY 会不起作用，即使我们没有查字表，每次都是 N+1SQL，例如：Elide 处理 JSONAPI 的时候。

> [官方文档](https://docs.jboss.org/hibernate/orm/5.0/topical/html/bytecode/BytecodeEnhancement.html)     

Hibernate 给我们提供了一种字节码增强技术，帮我们解决这种问题，通过编译器改变 class 解决。以备不时之需，实例如下：


```
grandle 添加如下配置
buildscript {
    ext {
        hibernateVersion = '5.2.16.Final'
    }
    dependencies {
        classpath("org.hibernate:hibernate-gradle-plugin:${hibernateVersion}")
    }
}
apply plugin: 'org.hibernate.orm'
hibernate {
    enhance {
        enableLazyInitialization = true
        enableDirtyTracking = false
        enableAssociationManagement = false
        enableExtendedEnhancement = true
    }
}
```
实体上添加 @Basic 和 @LazyToOne 注解：

```
@Entity
@Table(name = "user_tokens")
public class UserToken extends AbstractToken<User, UserToken> {
    private static final long serialVersionUID = -1184030877703854625L;
    private User user; 
    @ManyToOne(fetch = FetchType.LAZY)
    @Basic(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }
```
当我们 grandle build 完之后 class 中会变成如下内容：

```
jacks-Mac-mini:account-service jack$ javap build/classes/java/main/com/alo7/msa/account/domain/UserToken.class 
Compiled from "UserToken.java"
public class com.alo7.msa.account.domain.UserToken extends com.alo7.msa.account.domain.AbstractToken<com.alo7.msa.account.domain.User, com.alo7.msa.account.domain.UserToken> {
  public com.alo7.msa.account.domain.UserToken();
  public com.alo7.msa.account.domain.User getUser();
  public java.lang.Object $$_hibernate_getEntityInstance();
  public org.hibernate.engine.spi.EntityEntry $$_hibernate_getEntityEntry();
  public void $$_hibernate_setEntityEntry(org.hibernate.engine.spi.EntityEntry);
  public org.hibernate.engine.spi.ManagedEntity $$_hibernate_getPreviousManagedEntity();
  public void $$_hibernate_setPreviousManagedEntity(org.hibernate.engine.spi.ManagedEntity);
  public org.hibernate.engine.spi.ManagedEntity $$_hibernate_getNextManagedEntity();
  public void $$_hibernate_setNextManagedEntity(org.hibernate.engine.spi.ManagedEntity);
  public org.hibernate.engine.spi.PersistentAttributeInterceptor $$_hibernate_getInterceptor();
  public void $$_hibernate_setInterceptor(org.hibernate.engine.spi.PersistentAttributeInterceptor);
  public com.alo7.msa.account.domain.User $$_hibernate_read_user();
  public void $$_hibernate_write_user(com.alo7.msa.account.domain.User);
  public java.lang.Long $$_hibernate_read_refreshTokenId();
  public void $$_hibernate_write_refreshTokenId(java.lang.Long);
  public java.lang.String $$_hibernate_read_serviceName();
  public void $$_hibernate_write_serviceName(java.lang.String);
  public java.lang.String $$_hibernate_read_token();
  public void $$_hibernate_write_token(java.lang.String);
  public com.alo7.msa.account.util.enums.TokenType $$_hibernate_read_tokenType();
  public void $$_hibernate_write_tokenType(com.alo7.msa.account.util.enums.TokenType);
  public java.lang.Long $$_hibernate_read_expiresIn();
  public void $$_hibernate_write_expiresIn(java.lang.Long);
  public java.time.Instant $$_hibernate_read_deactivatedAt();
  public void $$_hibernate_write_deactivatedAt(java.time.Instant);
  public com.alo7.msa.account.util.enums.DeactivationReason $$_hibernate_read_deactivationReason();
  public void $$_hibernate_write_deactivationReason(com.alo7.msa.account.util.enums.DeactivationReason);
  public java.lang.String[] $$_hibernate_read_serviceScopes();
  public void $$_hibernate_write_serviceScopes(java.lang.String[]);
  public java.util.Set $$_hibernate_read_accessTokens();
  public void $$_hibernate_write_accessTokens(java.util.Set);
  public com.alo7.msa.account.domain.AbstractToken $$_hibernate_read_refreshToken();
  public void $$_hibernate_write_refreshToken(com.alo7.msa.account.domain.AbstractToken);
  public java.lang.Long $$_hibernate_read_userId();
  public void $$_hibernate_write_userId(java.lang.Long);
  public java.lang.String $$_hibernate_read_uuid();
  public void $$_hibernate_write_uuid(java.lang.String);
  public java.time.temporal.TemporalAccessor $$_hibernate_read_createdAt();
  public void $$_hibernate_write_createdAt(java.time.temporal.TemporalAccessor);
  public java.time.temporal.TemporalAccessor $$_hibernate_read_updatedAt();
  public void $$_hibernate_write_updatedAt(java.time.temporal.TemporalAccessor);
  public java.lang.Integer $$_hibernate_read_version();
  public void $$_hibernate_write_version(java.lang.Integer);
  public java.lang.Long $$_hibernate_read_id();
  public void $$_hibernate_write_id(java.lang.Long);
}
```
这样基本上可以解决问题，这个案例大家一般不常见，当大家遇到的时候想到有这一出即可。

### 四、Hibernate Inheritance Mapping 实战使用

在 Java 面向对象的语言环境，@entity 之间的关系多种多样，而根据 JPA 的规范，我们大致可以分为以下几种：

1. 纯粹的继承和表没关系，对象之间的字段共享。利用注解 @MappedSuperclass，父类不能是 @Entity；
2. 单表多态问题，同一张 Table，表示了不同的对象，通过一个字段来区分什么对象。利用 `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` 注解完成，只有父类有 @Table；
3. 多表多态，每一个子类一张表，父类的表拥有所有公用字段。通过 `@Inheritance(strategy = InheritanceType.JOINED)` 注解完成，父类和子类都是表，有公用的字段在父表里面；
4. Object 的继承，数据库里面表是每一张分开的，相互独立不影响。通过 `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)` 注解完成，父类（可以是一张表，也可以不是）和子类都是表，相互之间没有关系。

我们来实际看下这四种具体的详情。

#### 4.1 @MappedSuperclass

普通的继承用法，既公用的字段放在父类里面，但是父类不是一张表，每个子类都是一张表；可以用在实际的拆表场景，常见的是公用字段的用法，用来做框架的统一约定如下:

```
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAdvancedAuditable<T extends TemporalAccessor, U> extends AbstractSimpleAuditable<T> implements AdvancedAuditable<T, U> {
	private U createdBy;
	private U updatedBy;
	@CreatedBy
	@Column(name = "created_by")
	public U getCreatedBy() {
		return createdBy;
	}
	@Override
	@LastModifiedBy
	@Column(name = "updated_by")
	public U getUpdatedBy() {
		return updatedBy;
	}
}
```
父类做一些公用的代码封装；

#### 4.2 `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`

其实默认的 @MappedSuperClass 就是此种模式，当然了我们也可以这么用，要求继承基类的都是一个表如下：

```
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAdvancedAuditable<T extends TemporalAccessor, U> extends AbstractSimpleAuditable<T> implements AdvancedAuditable<T, U> {
	private U createdBy;
	private U updatedBy;
	@CreatedBy
	@Column(name = "created_by")
	public U getCreatedBy() {
		return createdBy;
	}
	@Override
	@LastModifiedBy
	@Column(name = "updated_by")
	public U getUpdatedBy() {
		return updatedBy;
	}
}
```
example2：

```
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal {
    @Id
    private long animalId;
    private String species;
    // constructor, getters, setters 
}
@Entity
public class Pet extends Animal {
    private String name;
    // constructor, getters, setters
}
@Entity
public class Pet extends Animal {
    private String name;
    // constructor, getters, setters
}
```
如果我们开 `spring.jpa.show-sql=true` 会帮我们生成 3 张互相没有关系的表。

#### 4.3 `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`

共用一张表，通过一个字段的不同值代表不同的对象，exmaple：

```
//书集合
@Entity(name="books")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="color", discriminatorType = DiscriminatorType.STRING)
public class Books {
    // ...
}
//红皮书集合
@Entity
@DiscriminatorValue("red")
public class RedBooks extends Books {
    // ...
}
//Yellow书集合
@Entity
@DiscriminatorValue("yellow")
public class YellowBooks extends Books {
    // ...
}
```
平时一般用在数据词典或者策略模式上。

#### 4.4 `@Inheritance(strategy = InheritanceType.JOINED)`

在这种映射策略里面，继承结构中的每一个实体（entity）类都会映射到数据库中一个单独的表中，也就是说每个实体（entity）都会被映射到数据库中，一个实体（entity）类对应数据库中的一个表。其中根实体（root entity）对应的表中定义了主键（primary key），所有的子类对应的数据库表都要共同使用这个主键，也可以同时这个表中和单表映射策略一样还定义了区分列（DTYPE）。

example：

```
//人，父亲表，公用person_id
@Entity
@Table(name="person")
@Inheritance(strategy=InheritanceType.JOINED)
public class Person {
 @Id
 @GeneratedValue
  @Column(name="person_id")
  private Long id;
 @Column(name="firstName")
 private String firstName;
 @Column(name="lastName")
private String lastName;
}
//老师student_id既是主键，又是外键
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "person_id")
public class Student extends Person {
 @Column(name = "standard")
 private String standard;
 @Column(name = "instructor")
 private String instructor;
}
//老师teacher_id既是主键，又是外键
@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "teacher_id", referencedColumnName = "person_id")
public class Teacher extends Person{
 @Column(name = "main_subject")
 private String mainSubject;
 @Column(name = "salary")
    private int salary;
}
```

留个问题思考一下，平时我们拆表怎么做？


### 五、@Transactional 在私有方法和异步线程遇到的问题？及其如何优雅解决？

Spring 官方明确说了在 private 方法和 protected 方法上直接使用 @Transactional 是不起作用的，那我们该怎么办呢？还有就是异步的时候如何管理 @Transactional。直接看做法吧：
 

#### 5.1 直接新建一个同步的 TransactionalHelper
```
@Service
public class TransactionalHelper {
    @Transactional(rollbackFor = Exception.class)
    public <T,R> R transactional(Function<T,R> function, T t){
        return function.apply(t);
    }
    @Transactional(rollbackFor = Exception.class)
    public <R> R transactional(Supplier<R> supplier){
        return supplier.get();
    }
    @Transactional(rollbackFor = Exception.class)
    public <T> void transactional(Consumer<T> consumer, T t){
        consumer.accept(t);
    }
}
```
#### 5.2 在新建一个异步 AsyncTransactionalHelper
```
@Component
public class AsyncTransactionalHelper {
    private TransactionalHelper transactionalHelper;
    @Autowired
    public AsyncTransactionalHelper(TransactionalHelper transactionalHelper) {
        this.transactionalHelper = transactionalHelper;
    }
    @Async
    public <T, R> R asyncExecuteTransaction(Function<T, R> function, T t) {
        return transactionalHelper.transactional(function, t);
    }
    @Async
    public <R> R asyncExecuteTransaction(Supplier<R> supplier) {
        return transactionalHelper.transactional(supplier);
    }
    @Async
    public <T> void asyncExecuteTransaction(Consumer<T> consumer, T t) {
        transactionalHelper.transactional(consumer, t);
    }
}
```

使用的地方如下：

```
//异步方法使用事务解决方法
    @Autowired
    private AsyncTransactionalHelper asyncTransactionalHelper;
    private void expireTokenAndSendLogoutMessage(DecodedJWT jwt, SessionExtraParameterDTO logoutExtraParameterDTO) {
        UserType userType = UserType.valueOf(jwt.getClaim(Constant.JWT_PAYLOAD_USER_TYPE).asString().toUpperCase());
        asyncTransactionalHelper.asyncExecuteTransaction(() -> {
            LoginService loginStrategy = loginService.getGenericLoginService(userType);
            loginStrategy.clearUserSessions(jwt.getId(), DeactivationReason.LOGOUT);
            return null;
        });
    }
    //同步方法使用事务使用解决方法:
    @Autowired
    private TransactionalHelper transactionalHelper;
     MessageRequest result = transactionalHelper.transactional(() -> {
        MessageRequest value = new MessageRequest();
        value.setUuid(UUID.randomUUID().toString());
        log.info("MessageRequest is save :{}", value);
        getDefaultRepository().save(value);
        return messageRequest;
    });
```

### 六、Repository 中 Cache 的使用

作者为什么要把 Cache 这个事情单独提一下呢?因为实际工作中发现很多人都把这个搞混掉了。其实 Spring Data JPA 里面有两种缓存机制：

1. 第一种：基于 Spring Data Cache 做的第三方缓存，又称分布式缓存，一般使用 Redis；
2. 第二种：就是我们常说的 Hibernate 的二级缓存，也是基于第三方的，一般是应用内的单应用缓存；

#### 第一种：Spring Data Cache

这种缓存机制，作者在书上已经有详细介绍了，就不重复那么多了啊，我直接来看一个使用的例子：

```
//注意这里使用的是springframework.cache
package hello;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
@Component
public class SimpleBookRepository implements BookRepository {
    @Override
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }
    //@Cacheable的语法及其丰富
    @Cacheable(value = "account", cacheManager = "redis.cache", unless = "#result == null")
    public String getUserUuidFrom(final String uid) {
        return executeCall(accountApi.getUserInfoById(uid)).getUuid();
    }
}
```
![enter image description here](https://images.gitbook.cn/f6d60680-8da2-11e8-bd0f-f38f8b899327)

使用方法很多特别是配置非常灵活；这个是跨进程的，分布式缓存；缺点只能在具体的 public 方法上面加 @Cache 注解才有效；有明确的过期时间，否则需要动态清除才过期。

#### 第二种：Hibernate 二级缓存

我们都知道 Hibernate 有一级缓存（Hibernate session 级别）和二级缓存（第三方缓存脱离 session 生命周期的，如对接 ehcache 等）。这个是单应用内的，不跨进程的，不需要动态清除，有 Hibernate 负责在 @Entity 更新的时候直接更新缓存，也可以依据 ehcache 的语法规则设置过期策略，尽量短，因为跨应用不好同步。使用方法如下：    

我们以 Ehcache 做二级缓存使用例子如下：


1) gradle 添加 cache 依赖
```
    compile('org.hibernate:hibernate-ehcache')
    compile('net.sf.ehcache:ehcache')
```
2) application.properties 配置如下
```
	//打开二级缓存
	spring.jpa.properties.hibernate.cache.use_second_level_cache=true
	//打开查询缓存
	spring.jpa.properties.hibernate.cache.use_query_cache=true
	//指定缓存provider
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory
	//配置shared-cache-mode
	spring.jpa.properties.javax.persistence.sharedCache.mode=ENABLE_SELECTIVE
```
3) ehcache 设置
```
	<!-- 缓存模版,此处为了显示其用法,也可以不用模版直接在cache中配置与模版参数相同 -->
	<cache-template name="template">
		<key-type>java.lang.String</key-type>
		<value-type>java.lang.String</value-type>
		<expiry>
			<!-- 单位默认为秒当用秒作单位时,可以不填-->
			<ttl unit="hours">1</ttl>
		</expiry>
		<resources>
			<!-- 单位默认为entries当用entries作单位时,可以不填-->
			<heap>1</heap>
			<offheap unit="MB">1</offheap>
			<!-- persistent 默认为false可以不填-->
			<disk unit="MB">20</disk>
		</resources>	
	</cache-template>
```
4) entity 里面的使用，Collection Cache
```
	@Entity
	@Cacheable
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public class Foo {
	    ...
	    @Cacheable
	    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	    @OneToMany
	    private Collection<Bar> bars;
	    // getters and setters
	}
	//直接在Entity上面添加缓存
	@Entity
	@Cacheable
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public class Foo {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "ID")
	    private long id;
	    @Column(name = "NAME")
	    private String name;
	}
```
CacheConcurrencyStrategy 的四种策略：

1. `CacheConcurrencyStrategy.READ_ONLY`：只读模式，在此模式下，如果对数据进行更新操作，会有异常；
1. `CacheConcurrencyStrategy.READ_WRITE`：读写模式在更新缓存的时候会把缓存里面的数据换成一个锁，其它事务如果去取相应的缓存数据，发现被锁了，直接就去数据库查询；
1. `CacheConcurrencyStrategy.NONSTRICT_READ_WRITE`：不严格的读写模式则不会的缓存数据加锁；
1. `CacheConcurrencyStrategy.TRANSACTIONAL`：事务模式指缓存支持事务，当事务回滚时，缓存也能回滚，只支持 JTA 环境。

需要注意的是，两种缓存机制不能一起使用，否则会乱套。

### 七：Hibernate、Session、SessionFactory、EntityManager 关系是什么样的？

![SessionImpl](https://images.gitbook.cn/0649bb20-8dc1-11e8-80d1-2d51ff7e1c55)

一图胜千言，我们通过上面的 UML 图来看下我们要关心的几个类的关系情况；

1. Hibernate 是基于 SessionFactory 管理 Session 来获得事务的；
2. Spring Data JPA 是基于 EntityManagerFactory 管理 EntityManager 来获得事务的；
3. Session 继承 EntityManager，拥有共同的实现类 SessionImpl;
4. 而 JPA 的整体实现都是通过 SimpleJpaRepostory 调用 EntityManager 来做的；

而 Session 和 EntityManager 在什么时机被获取有两条线路：  

**1) Hibernate 传统的加载路线：**

1.事务加载时机：HibernateTransactionManager 管理 SessionFactory，获得 Session 从而开启事务；通过 
```
TransactionSynchronizationManager.getResource(sessionFactory)
private static final ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");
```
来获得当前现场的 SessionHolder，从而获得 session。

2.filter 加载时机：OpenSessionInViewFilter 默认没有开启，代码逻辑基本同上面相同；

3.Interceptor 时机加载 Session：通过 OpenSessionInViewInterceptor 实现，默认没有加载；

**2) JPA 这条加载线路：**

1.事务加载时机：JpaTransactionManager 管理 EntityManagerFactory，获得 EntityManager 从而开启事务：关键代码：
```
//JpaTransactionManager开启事务方法
@Override
	protected Object doGetTransaction() {
		JpaTransactionObject txObject = new JpaTransactionObject();
		txObject.setSavepointAllowed(isNestedTransactionAllowed());
		EntityManagerHolder emHolder = (EntityManagerHolder)
				TransactionSynchronizationManager.getResource(obtainEntityManagerFactory());
.....
}
//TransactionSynchronizationManager.getResource关键代码
private static final ThreadLocal<Map<Object, Object>> resources =
			new NamedThreadLocal<>("Transactional resources");
	@Nullable
	public static Object getResource(Object key) {
		Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
		Object value = doGetResource(actualKey);
		if (value != null && logger.isTraceEnabled()) {
			logger.trace("Retrieved value [" + value + "] for key [" + actualKey + "] bound to thread [" +
					Thread.currentThread().getName() + "]");
		}
		return value;
	}
	@Nullable
	private static Object doGetResource(Object actualKey) {
		Map<Object, Object> map = resources.get();
    }
    ......
```
来获得当前现场的 EntityManagerHolder，从而获得 EntityManager。

2.filter 加载时机： OpenEntityManagerInViewFilter 默认没有开启，代码逻辑基本同上面相同；

3.Interceptor 时机加载 EntityManager：通过 OpenEntityManagerInViewInterceptor 实现，默认开启加载；加载逻辑在 JpaBaseConfiguration 类里面。


### 八：透过现象看本质，思想上我们如何形成转变？

1. 用面向对象的思路来做 DB 操作，而不是一上来我们就想着如何通过 SQL 我们该怎么写；
2. 当我们哪里用的复杂的时候，一定是哪里不对了，应该有更好的实践方法；
3. 对 @Entity 要认识到本质，@Entity 其实能充分体现设计者的设计思路，比如说 DDD，是设计成贫血模式，还是该用充血模式？我们设计表的时候是否应该遵循数据库的三大范式？这些都是要思考的；
4. JPA 的实质是理解设计思想，而 Hibernate 我们要能很好的掌握 @Entity 的生命周期，这是本质；
5. 事务、session、connection 的原理是什么，站在全局去看 Spring Data JPA 这件事情；
6. 先掌握大局和理论解决的是什么问题，然后针对细节问题我们逐步去找方案就行了；

### 最后
老师的新书[《Spring Data JPA 从入门到精通》](https://s.click.taobao.com/LsfmVOw)，可以跟着老师一起从下往上学习。

![《Spring Data JPA 从入门到精通》](http://images.gitbook.cn/15d12610-5c1f-11e8-b5c3-3fe2bc231f99)

#### 福利

参加本 chat 的小伙伴们有福利了，当天交流的时候，尾部有彩蛋，赠书一本书，条件是：  

1. 至少参加过老师的一篇达人课。 
2. 至少有老师的三个 5 星的 chat 评价。
3. 本 chat 分享朋友圈 30 个赞。

（本活动截止时间是 Chat 分享当天晚上 9:40。）条件都达到的取朋友圈点赞数最多的第一名。赠书《Spring Data JPA入门到精通》一本。详情可以问 chat 小编。

> 如果大家有问题需要互相交流，可以加以下两个群。

>- QQ交流群1：240619787
>- QQ交流群2：559701472

> 欢迎大家通过各种渠道一起交流；


----------
本文首发于GitChat，未经授权不得转载，转载需与GitChat联系。

