package com.example.jpa.example1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JpaConfiguration.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @MockBean
    MyAuditorAware myAuditorAware;

    /**
     * 为了和测试方法的事务分开，我们在init里面初始化数据做新增 操作
     */
    @BeforeAll
    @Rollback(false)
    @Transactional
    public void init() {
        //由于测试用例模拟web context 环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回13这个用户ID
        Mockito.when(myAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(13));

        User u1 = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        //没有save之前 version是null
        Assertions.assertNull(u1.getVersion());
        try {
            userRepository.save(u1);
        } catch (Exception e) {
            System.out.println("************************");
        }
        //这里面触发保存方法，这个时候version里面被我们设置成了1；我们验证一下
//        Assertions.assertEquals(1,u1.getVersion());
    }

    /**
     * 测试一下更新和查询
     */
    @Test
    @Rollback(false)
    @Transactional
    public void testCallBackUpdate() {
        //此时会触发@PostLoad事件
        User u1 = userRepository.getOne(1L);
        //我们从db里面重新查询出来，验证一下version是不是1
        Assertions.assertEquals(1,u1.getVersion());
        u1.setSex(SexEnum.GIRL);
        //此时会触发@PreUpdate事件
        userRepository.save(u1);
        List<User> u3 = userRepository.findAll();
        u3.stream().forEach(u->{
            //我们从db查询出来，验证一下CcreateUserId是否是我们刚才修改的200
           Assertions.assertEquals(200,u.getCreateUserId());
        });
    }

    /**
     * 测试一下删除事件
     */
    @Test
    @Rollback(false)
    @Transactional
    public void testCallBackDelete() {
        //此时会触发@PostLoad事件
        User u1 = userRepository.getOne(1L);
//        Assertions.assertEquals(200,u1.getCreateUserId());
        userRepository.delete(u1);
        //此时会触发@PreRemove @PostRemove事件
        System.out.println("delete_after::");

    }
}
