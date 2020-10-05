package com.example.jpa.example1;

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
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JpaConfiguration.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @MockBean
    MyAuditorAware myAuditorAware;

    @BeforeAll
    @Rollback(false)
//    @Transactional
    public void init() {
        //由于测试用例模拟web context 环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回13这个用户ID
        Mockito.when(myAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(13));

        //我们没有显式的指定更新时间，创建时间，更新人，创建人
        User u1 = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        System.out.println("save_before:: user param version is:"+u1.getVersion());
        userRepository.save(u1);
        System.out.println("save_after:: user param version is:"+u1.getVersion());
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testCallBack() {
        User u1 = userRepository.getOne(1L);
        System.out.println("find_after:: user db version is: "+u1.getVersion());
        u1.setSex(SexEnum.GIRL);
        userRepository.save(u1);
        System.out.println("save_after:: user db version is: "+u1.getVersion());
        User u3 = userRepository.getOne(1L);
        System.out.println("find_after:: user db version is: "+u3.toString());
//        userRepository.delete(u2);
//        System.out.println("delete_after::");

    }
}
