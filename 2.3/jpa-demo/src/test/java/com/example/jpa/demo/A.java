package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class A {
    public static void main(String[] args) {
        //提前准备一些数据方便我们测试
        UserInfo u1 = UserInfo.builder().id(1L).lastName("jack").version(1).build();
        UserInfo u2 = UserInfo.builder().id(1L).lastName("jack2").version(1).build();
        List<UserInfo> u = Lists.newArrayList(u1,u2);
        Map<Long, UserInfo> rawMap = u.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        rawMap.keySet().forEach(k->{
            System.out.println(k);
        });
    }
}
