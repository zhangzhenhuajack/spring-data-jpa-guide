package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceProperty;
import javax.transaction.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Import(TestConfiguration.class)
@ComponentScan(value = "com.example.jpa.demo.config.DemoProperties")
//@AutoConfigureTestDatabase
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
            (properties = {@PersistenceProperty(
            name = "org.hibernate.flushMode",
            value = "MANUAL"//手动flush
    )})
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
    public void testLife() {
        UserInfo userInfo = UserInfo.builder().name("new name").build();
        //新增一个对象userInfo交给PersistenceContext管理，既一级缓存
        entityManager.persist(userInfo);
        //此时没有detach和clear之前，flush的时候还会产生更新SQL
        userInfo.setName("old name");
        entityManager.flush();
        entityManager.clear();
//        entityManager.detach(userInfo);
        // entityManager已经clear，此时已经不会对UserInfo进行更新了
        userInfo.setName("new name 11");
        entityManager.flush();

        //由于有cache机制，相同的对象查询只会触发一次查询SQL
        UserInfo u1 = userInfoRepository.findById(1L).get();
        //to do some thing
        UserInfo u2 = userInfoRepository.findById(1L).get();
    }

    @TestConfiguration
    static class TestConfig {
//        @Bean
//        public DemoProperties demoProperties () {
//            return new DemoProperties();
//        }
    }
}
