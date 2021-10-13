package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceProperty;
import javax.transaction.Transactional;
<<<<<<< HEAD
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
=======
import java.time.Instant;
>>>>>>> 11899e64a2861c290c93fb0243556bd288c2f3d6

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInfoRepositoryTest {


    @Autowired
    private UserInfoRepository userInfoRepository;
    //测试一些手动flush的机制
    @PersistenceContext
            (properties = {@PersistenceProperty(
                    name = "org.hibernate.flushMode",
                    value = "MANUAL"//手动flush
            )})
    private EntityManager entityManager;


<<<<<<< HEAD
//    @BeforeAll
//    @Rollback(false)
//    @Transactional
//    public void init() {
//
//    }

=======
>>>>>>> 11899e64a2861c290c93fb0243556bd288c2f3d6
    @Test
    @Transactional
    @Rollback(value = false)
    public void testLife() {
<<<<<<< HEAD
        //提前准备一些数据方便我们测试
        UserInfo u1 = UserInfo.builder().id(1L).lastName("jack").version(1).build();
        UserInfo u2 = UserInfo.builder().id(1L).lastName("jack2").version(1).build();
//        userInfoRepository.save(u1);
        List<UserInfo> u = Lists.newArrayList(u1,u2);
        Map<Long, UserInfo> rawMap = u.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        rawMap.keySet().forEach(k->{
            System.out.println(k);
        });

//        UserInfo userInfo = UserInfo.builder().name("new name").build();
//        //新增一个对象userInfo交给PersistenceContext管理，既一级缓存
//        entityManager.persist(userInfo);
//        //此时没有detach和clear之前，flush的时候还会产生更新SQL
//        userInfo.setName("old name");
//        entityManager.flush();
//        entityManager.clear();
////        entityManager.detach(userInfo);
//        // entityManager已经clear，此时已经不会对UserInfo进行更新了
//        userInfo.setName("new name 11");
//        entityManager.flush();
//
//        //由于有cache机制，相同的对象查询只会触发一次查询SQL
//        UserInfo u1 = userInfoRepository.findById(1L).get();
//        //to do some thing
//        UserInfo u2 = userInfoRepository.findById(1L).get();
=======
        UserInfo userInfo = UserInfo.builder().name("new name").build();
        entityManager.persist(userInfo);
        userInfo.setName("old name");
        entityManager.flush();
        userInfo.setName("new name 11");
        entityManager.flush();
//        Instant instant = Instant.from()
>>>>>>> 11899e64a2861c290c93fb0243556bd288c2f3d6
    }
}
