#### 1. 首先我们要高明白，equals和hashCode的作用：
`参考：[https://www.journaldev.com/21095/java-equals-hashcode](https://www.journaldev.com/21095/java-equals-hashcode)`
![](http://www.jackzhang.cn/spring-data-jpa-guide/images/equals/1.png)
````
我们要明白几点：
1. equals和hashcode要同时实现；
2. 默认object类里面的是引用地址比较；
````
    public boolean equals(Object obj) {
        return (this == obj);
    }

````
````
![](http://www.jackzhang.cn/spring-data-jpa-guide/images/equals/eclipse-generate-hashcode-equals.png)
可以通过开发者工具实现；

##### 2. hibernatre里面我们如何去实现equals和hashCode;
````
参考：
https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
````
##### Fixing the entity identifier equals and hashCode
To address the previous issue, there is only one solution: the hashCode should always return the same value:
````
@Entity
public class Book implements Identifiable<Long> {
 
    @Id
    @GeneratedValue
    private Long id;
 
    private String title;
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (!(o instanceof Book))
            return false;
 
        Book other = (Book) o;
 
        return id != null &&
               id.equals(other.getId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
 
    //Getters and setters omitted for brevity
}
````
Also, when the entity identifier is null, we can guarantee equality only for the same object references. Otherwise, no transient object is equal to any other transient or persisted object. That’s why the identifier equality check is done only if the current Object identifier is not null.

With this implementation, the equals and hashCode test runs fine for all entity state transitions. The reason why it works is that the hashCode value does not change, hence, we can rely on the java.lang.Object reference equality as long as the identifier is null.

###### Natural id
The first use case to test is the natural id mapping. Considering the following entity:

````
@Entity
public class Book implements Identifiable<Long> {
 
    @Id
    @GeneratedValue
    private Long id;
 
    private String title;
 
    @NaturalId
    private String isbn;
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getIsbn());
    }
 
    //Getters and setters omitted for brevity
}
````
The isbn property is also a @NaturalId, therefore, it should be unique and not nullable. Both equals and hashCode use the isbn property in their implementations.
##### 3. 应用场景
1. 期待跨session共享entity的时候，并且是用set或者类似hashTable的时候，不期待业务对象重复，可以考虑。
2. 一般情况下个人不建议去实现这两个，不过也有人说最好实现，但是个人观点当用到这个场景再说，没必要一定要去实现，这个欢迎大家一起讨论

