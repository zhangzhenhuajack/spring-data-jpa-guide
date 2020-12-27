package com.example.jpa.demo;

import com.example.jpa.demo.service.UserInfoService;
import com.example.jpa.demo.service.dto.UserInfoDto;
import com.example.jpa.demo.web.UserInfoController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserInfoService userInfoService;

    //单元测试 mvc的controller的方法
    @Test
    public void testGetUserDto() throws Exception {
        //利用@MockBean，当调用 userInfoService的的findByUserId(1)的时候返回一个模拟的UserInfoDto数据
        Mockito.when(userInfoService.findByUserId(1L)).thenReturn(UserInfoDto.builder().name("jack").id(1L).build());

        //利用mvc验证一下Controller里面的解决是否OK
        MockHttpServletResponse response = mvc
                .perform(MockMvcRequestBuilders
                        .get("/user/1/")//请求的path
                        .accept(MediaType.APPLICATION_JSON)//请求的mediaType，这里面可以加上各种我们需要的Header
                )
                .andDo(print())//打印一下
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("jack"))
                .andReturn().getResponse();
        System.out.println(response);
    }
}
