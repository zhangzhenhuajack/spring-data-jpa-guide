package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserOnlyNameEmailEntityRepository userOnlyNameEmailEntityRepository;

    @BeforeAll
    @Rollback(false)
    @Transactional()
    void init() {
        User user = User.builder().name("jackxx").email("123456@126.com").build();
        UserInfo userInfo = UserInfo.builder().ages(12).user(user).telephone("12345678").build();
        userInfoRepository.saveAndFlush(userInfo);
        userRepository.save(User.builder().name("jackxx").email("123456@126.com").build());
        userRepository.save(User.builder().name("jackxx").email("123456@126.com").build());
    }
    /**
     * 测试用User关联关系操作
     *
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testUserRelationships() throws JsonProcessingException {
//        User user1 = userRepository.getOne(3L);
//        System.out.println(user1.getName());
        
//        userInfo.setAges(13);
//        userInfo.setUser(null);
//        userInfoRepository.delete(userInfo);
//        User user = userRepository.save();
//        Assert.assertNotNull(user);
//        User user1 = userRepository.getOne(1L);
//        System.out.println(user1);
        UserInfo userInfo1 = userInfoRepository.getOne(1L);
        System.out.println(userInfo1);
        System.out.println(userInfo1.getUser());
    }
    @Test
    public void testSaveUser() throws JsonProcessingException {
//        User user = userRepository.save(User.builder().name("jackxx").email("123456@126.com").sex("man").address("shanghai").build());
//        Assert.assertNotNull(user);
//        User user2 = userRepository.save(User.builder().version(1L).id(1L).name("jackffff").email("123456@126.com").build());
//        User user = userRepository.save(User.builder().name("jackxx").email("123456@126.com").sex("man").address("shanghai").build());
//        Assert.assertNotNull(user);
//        List<User> users= userRepository.findAll();
//        System.out.println(users);
//        Assert.assertNotNull(users);
//
//        UserDto userDto = userRepository.findBySex("man");
//        System.out.println(userDto);
//
//        UserOnlyName userOnlyName = userRepository.findByAddress("shanghai");
//        System.out.println(userOnlyName.getName()+"==============");

//        UserDto userDtox = userRepository.findByEmail("123456@126.com");
//        System.out.println(userDtox);
    }

    @Test
    public void testProjections() {
//        userRepository.save(User.builder().id(1L).name("jack12").email("123456@126.com").sex("man").address("shanghai").build());
//        List<User> users= userRepository.findAll();
//        System.out.println(users);
//        UserOnlyNameEmailEntity uName = userOnlyNameEmailEntityRepository.getOne(1L);
//        System.out.println(uName);
//        userRepository.save(User.builder().id(1L).name("jack12").email("123456@126.com").sex("man").address("shanghai").build());
//        UserOnlyNameEmailDto userOnlyNameEmailDto =  userRepository.findByEmail("123456@126.com");
//        System.out.println(userOnlyNameEmailDto);

        userRepository.save(User.builder().name("jack12").email("123456@126.com").sex("man").address("shanghai").build());
        UserOnlyName userOnlyName = userRepository.findByAddress("shanghai");
        System.out.println(userOnlyName);
    }

    @Test
    public void testQueryUser() throws JsonProcessingException {
        //我们新增7条数据方便测试分页结果
        userRepository.save(User.builder().name("jack1").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack2").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack3").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack4").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack5").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack6").email("123456@126.com").sex("man").address("shanghai").build());
        userRepository.save(User.builder().name("jack7").email("123456@126.com").sex("man").address("shanghai").build());

        //test StreamAble
        Streamable<User> userStreamable = userRepository.findAll(PageRequest.of(0, 10)).and(User.builder().name("jack222").build());
        userStreamable.forEach(System.out::println);

        //我们利用ObjectMapper将我们的返回结果Json to String
        ObjectMapper objectMapper = new ObjectMapper();

        //返回Stream类型结果（1）
//        Stream<User> userStream = userRepository.findAllByCustomQueryAndStream(PageRequest.of(1,3));
//        userStream.forEach(System.out::println);
        //返回分页数据（2）
        Page<User> userPage = userRepository.findAll(PageRequest.of(0, 3));
        System.out.println(objectMapper.writeValueAsString(userPage));
        //返回Slice结果（3）
//        Slice<User> userSlice = userRepository.findAllByCustomQueryAndSlice(PageRequest.of(1,3));
//        System.out.println(objectMapper.writeValueAsString(userSlice));
        //返回List结果（4）
        List<User> userList = userRepository.findAllById(Lists.newArrayList(1L, 2L));
        System.out.println(objectMapper.writeValueAsString(userList));
    }


}
