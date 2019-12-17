##### 1)当提交事务的时候，触发的flush会，回来检查是否有脏数据；
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/1.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/2.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/3.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/4.png)
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/5.png)
##### 既：得出一个结论，再flush的时候hibernate会一个个判断哪个属性发生变化了，如果没有发生变化，则不产生update的sql语句。只有变化才会才生 update sql ,并且可以做到同一个事务里面的多次update合并。   
##### 一次save操作栈的执行过程：
![](https://github.com/zhangzhenhuajack/spring-data-jpa-guide/blob/master/images/entity_dirty/6.png)