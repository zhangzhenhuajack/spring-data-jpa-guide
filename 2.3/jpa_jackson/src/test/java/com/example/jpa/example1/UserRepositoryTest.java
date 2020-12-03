package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
//        UserInfo userInfo1 = userInfoRepository.getOne(2L);
        User user = userRepository.getOne(2L);
        System.out.println(user.getName());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //遇到不可识别字段的时候不要报错，因为前端传进来的字段不可信，可以不要影响正常业务逻辑
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //遇到不可以识别的枚举的时候，为了保证服务的强壮性，建议也不要关心未知的，甚至给个默认的，特别是微服务大家的枚举值随时在变，但是老的服务不需要跟着一起变的
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE,true);
//        objectMapper.registerModule(new Hibernate5Module());
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

//        System.out.println(userInfo1);
//        System.out.println(userInfo1.getUser().getId());

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
