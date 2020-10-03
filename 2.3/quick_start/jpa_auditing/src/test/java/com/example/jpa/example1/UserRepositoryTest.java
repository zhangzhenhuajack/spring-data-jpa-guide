package com.example.jpa.example1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

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
    @Test
    public void testAuditing() {
        //由于测试用例模拟web context 环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回13这个用户ID
        Mockito.when(myAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(13));
        //我们没有显式的指定更新时间，创建时间，更新人，创建人
        User user = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        userRepository.save(user);
        //验证是否有创建时间，更新时间，UserID是否正确；
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(13,users.get(0).getCreateUserId());
        Assertions.assertNotNull(users.get(0).getLastModifiedTime());
        System.out.println(users.get(0));
    }
}
