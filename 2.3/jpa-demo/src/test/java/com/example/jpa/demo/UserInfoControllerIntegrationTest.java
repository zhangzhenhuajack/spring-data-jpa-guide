package com.example.jpa.demo;

import com.example.jpa.demo.service.dto.UserInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //加載DemoApplication，指定一个随机端口
public class UserInfoControllerIntegrationTest {
    @LocalServerPort //获得模拟的随机端口
    private int port;

    @Autowired //我们利用RestTemplate，发送一个请求
    private TestRestTemplate restTemplate;

    @Test
    public void testAllUserDtoIntegration() {
        UserInfoDto userInfoDto = this.restTemplate
                .getForObject("http://localhost:" + port + "/user/1", UserInfoDto.class);//真实请求有一个后台的API
        Assertions.assertNotNull(userInfoDto);
    }
}
