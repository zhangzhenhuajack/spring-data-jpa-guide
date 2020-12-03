package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder().name("jackxx").email("123456@126.com").build();
        UserInfo userInfo = UserInfo.builder().ages(12).user(user).telephone("12345678").build();
        userInfoRepository.saveAndFlush(userInfo);
    }
    /**
     * 测试用User关联关系操作
     *
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testUserManyToMany() throws JsonProcessingException {

    }
    /**
     * 测试用User关联关系操作
     *
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testUserRelationships() throws JsonProcessingException {
        UserInfo userInfo1 = userInfoRepository.getOne(1L);
        System.out.println(userInfo1);
        System.out.println(userInfo1.getUser().getId());
//        System.out.println(userInfo1.getUser().getName());

//        User user1 = userRepository.getOne(3L);
//        System.out.println(user1.getName());


//        User user = User.builder().name("jackxx").email("123456@126.com").build();
//        UserInfo userInfo = UserInfo.builder().ages(12).user(user).telephone("12345678").build();
//        userInfoRepository.saveAndFlush(userInfo);
//        userInfo.setAges(13);
//        userInfo.setUser(null);
//        userInfoRepository.delete(userInfo);
//        User user = userRepository.save();
//        Assert.assertNotNull(user);
//        User user1 = userRepository.getOne(1L);
//        System.out.println(user1);

//        UserInfo userInfo1 = userInfoRepository.getOne(1L);
//        System.out.println(userInfo1);
//        System.out.println(userInfo1.getUser());
    }
}
