package com.example.jpa.demo;

import com.example.jpa.demo.config.DemoProperties;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Import(TestConfiguration.class)
@ComponentScan(value = "com.example.jpa.demo.config.DemoProperties")
//@AutoConfigureTestDatabase
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;
//    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
////            (properties = {@PersistenceProperty(
////            name = "org.hibernate.flushMode",
////            value = "MANUAL"//手动flush
////    )})
//    private EntityManager entityManager;


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
    public void testEntityName() {
        userInfoRepository.findByTable("user_info");
//        userInfoRepository.findBySystemUser();
//        userInfoRepository.findByRootUser();
        userInfoRepository.findUsersByFirstnameForSpELExpressionWithParameterIndexOnlyWithEntityExpression("1","2");
        userInfoRepository.findByNameWithSpelExpression("J");
        userInfoRepository.findOliverBySpELExpressionWithoutArgumentsWithQuestionmark();
        userInfoRepository.findUsersByCustomersFirstname(UserInfo.builder().name("kk").build());
        userInfoRepository.findAllByEntityName();
        userInfoRepository.findContainingEscaped("jack");
        userInfoRepository.findByNameWithSpelExpression("JK");
    }
    @TestConfiguration
    static class TestConfig {
        @Bean
        public DemoProperties demoProperties () {
            return new DemoProperties();
        }
    }
}
