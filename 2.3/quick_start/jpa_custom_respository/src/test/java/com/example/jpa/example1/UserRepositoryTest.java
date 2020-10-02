package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    private Date now = new Date();
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .createDate(Instant.now())
                .updateDate(now)
                .build();
        userAddressRepository.saveAll(Lists.newArrayList(UserAddress.builder().user(user).address("shanghai").build(),
                UserAddress.builder().user(user).address("beijing").build()));

    }

    @Test
    public void testCustomizedUserRepository() {
        User user = userRepository.findById(2L).get();
        userRepository.logicallyDelete(user);

        List<User> users = userRepository.findAll();
        Assertions.assertEquals(users.get(0).getDeleted(),Boolean.TRUE);
    }

    @Test
    public void testCustomizedBaseRepository() {
        User user = userRepository.findById(2L).get();
        userRepository.logicallyDelete(user);

        userRepository.delete(user);
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(users.get(0).getDeleted(),Boolean.TRUE);
    }


    /**
     * 测试entityManager用法
     *
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testEntityManager() throws JsonProcessingException {
        //测试找到一个User对象
        User user = entityManager.find(User.class,2L);
        Assertions.assertEquals(user.getAddresses(),"shanghai");

        //我们改变一下user的删除状态
        user.setDeleted(true);
        //merger方法
        entityManager.merge(user);
        //更新到数据库里面
        entityManager.flush();

        //在通过createQuery创建一个JPQL，进行查询
        List<User> users =  entityManager.createQuery("select u From User u where u.name=?1")
                .setParameter(1,"jack")
                .getResultList();
        Assertions.assertTrue(users.get(0).getDeleted());
    }
}
