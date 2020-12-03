package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
//            (properties = {@PersistenceProperty(
//            name = "org.hibernate.flushMode",
//            value = "MANUAL"//手动flush
//    )})
    private EntityManager entityManager;


    @BeforeAll
    @Rollback(false)
    @Transactional
    public void init() {

        UserInfo u1 = UserInfo.builder().id(1L).lastName("jack").version(1).build();

        try {
            userInfoRepository.save(u1);
        } catch (Exception e) {
            System.out.println("************************");
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testDirty() {
        UserInfo userInfo = userInfoRepository.findById(1L).get();
        userInfo.setLastName("jack_test_dirty");
        userInfoRepository.saveAndFlush(userInfo);
    }


    @Test
    @Rollback(value = false)
    public void testMergeException() {
        //通过new的方式构建一个游离状态的对象
        UserInfo userInfo = UserInfo.builder().id(1L).lastName("jack").version(1).build();
        //验证是否存在于persistence context 里面，new的肯定不存在
        Assertions.assertFalse(entityManager.contains(userInfo));
        //当执行persist方法的时候就会报异常
//        Assertions.assertThrows(PersistentObjectException.class,()->entityManager.persist(userInfo));
        //detached状态的实体通过merge的方式保存在了persistence context里面了
       UserInfo userInfo2 = entityManager.merge(userInfo);
        entityManager.flush();
        //验证一下存在于持久化上下文里面
        Assertions.assertTrue(entityManager.contains(userInfo2));
//        entityManager.detach(userInfo);
        entityManager.clear();
    }

    @Test
    @Rollback(value = false)
    public void testDelete() {
        UserInfo userInfo = UserInfo.builder().lastName("jack").build();
        entityManager.persist(userInfo);

        entityManager.flush();
        System.out.println("执行了flush()方法，产生了insert sql");
        Assertions.assertTrue(entityManager.contains(userInfo));
        entityManager.remove(userInfo);
        Assertions.assertFalse(entityManager.contains(userInfo));
        entityManager.flush();
        System.out.println("执行了flush()方法之后，又产生了delete sql");
        Assertions.assertFalse(entityManager.contains(userInfo));
    }

    @Test
    @Rollback(value = false)
    public void testManagerException() {
        UserInfo userInfo = UserInfo.builder().lastName("jack").build();
        entityManager.persist(userInfo);
        System.out.println("没有执行 flush()方法，产生insert sql");
        entityManager.flush();
        System.out.println("执行了flush()方法，产生了insert sql");
        Assertions.assertTrue(entityManager.contains(userInfo));
    }


    @Test
    @Transactional
//    @Rollback(value = false)
    public void testFlushOrder() {
//        UserInfo u3 = UserInfo.builder().id(3L).lastName("jack").version(1).build();
//        entityManager.merge(u3);
        UserInfo u2 = UserInfo.builder().id(2L).lastName("jack").version(1).build();
        UserInfo u3 = entityManager.merge(u2);
        entityManager.flush();
//        UserInfo u1 = UserInfo.builder().id(1L).lastName("jack").version(1).build();
//        entityManager.merge(u1);
//        UserInfo u1 = UserInfo.builder().id(2L).lastName("jack").version(1).build();
        entityManager.remove(u3);
        UserInfo userInfo = UserInfo.builder().lastName("jack").build();
        entityManager.persist(userInfo);
//        userInfoRepository.queryByFlushTest();//是操作JPQL的，这个就会先触发flush操作；


    }
    @Test
    public void testPersist() {
        UserInfo userInfo = UserInfo.builder().lastName("jack").build();
        //通过contains方法可以验证对象是否在PersistenceContext里面，此时不在
        Assertions.assertFalse(entityManager.contains(userInfo));
        //通过persist方法把对象放到PersistenceContext里面
        entityManager.remove(userInfo);//是直接操作Entity的，不会触发flush操作
//        entityManager.persist(userInfo);//是直接操作Entity的，不会触发flush操作
        System.out.println("没有执行 flush()方法，产生insert sql");
//        UserInfo userInfo2 = entityManager.find(UserInfo.class,2L);//是直接操作Entity的，这个就不会触发flush操作
//        userInfoRepository.queryByFlushTest();//是操作JPQL的，这个就会先触发flush操作；
        System.out.println("flush()方法，产生insert sql");

        //通过contains方法可以验证对象是否在PersistenceContext里面，此时在
        Assertions.assertTrue(entityManager.contains(userInfo));
        Assertions.assertNotNull(userInfo.getId());

    }
}
