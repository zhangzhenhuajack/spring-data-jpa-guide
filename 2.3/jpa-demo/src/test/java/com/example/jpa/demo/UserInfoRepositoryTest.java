package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
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
import java.time.Instant;

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


    @Test
    @Transactional
    @Rollback(value = false)
    public void testLife() {
        UserInfo userInfo = UserInfo.builder().name("new name").build();
        entityManager.persist(userInfo);
        userInfo.setName("old name");
        entityManager.flush();
        userInfo.setName("new name 11");
        entityManager.flush();
//        Instant instant = Instant.from()
    }
}
