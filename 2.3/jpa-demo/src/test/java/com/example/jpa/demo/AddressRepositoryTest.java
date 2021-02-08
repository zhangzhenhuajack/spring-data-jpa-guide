package com.example.jpa.demo;

import com.example.jpa.demo.db.Address;
import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @BeforeAll //利用 @BeforeAll准备一些Repositroy需要的测数据
    @Rollback(false)// 由于每个方法都是有事务回滚机制的，我们为了测试我们的Repository可能需要模拟一些数据，所以我们改变回滚机制
    @Transactional
    public void init() {

        Address address = Address.builder().city("shanghaiDeleted").deleted(true).build();
        addressRepository.save(address);
    }

    //测试没有包含删除的记录
    @Test
    public  void testFindAllNoDeleted() {
        //ExpressionParser是操作SpEL的总入口，创建一个接口ExpressionParser对应的实例SpelExpressionParser
        ExpressionParser parser = new SpelExpressionParser();
//通过上面我们讲的parser.parseExpression方法获得一个Expression的实例，里面实现的就是new一个SpelExpression对象；而parseExpression的参数就是SpEL的使用重点，各种表达式的字符串
//1.简单的string类型用'' 引用
//        Expression exp = parser.parseExpression("'Hello World'");
//2.SpEL支持很多功能特性，如调用方法、访问属性、调用构造函数，我们可以直接调用String对象里面的concat方法进行字符串拼接
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");

//通过getValue方法可以得到经过Expresion计算parseExpression方法的字符串参数(符合SpEL语法的表达式)的结果
        String message = (String) exp.getValue();
        System.out.println(message);

//        addressRepository.findByAddress("shanghai", PageRequest.of(1,5));
//        List<Address> address1 = addressRepository.findAll();
//        int deleteSize = address1.stream().filter(d->d.equals("shanghaiDeleted")).collect(Collectors.toList()).size();
//        Assertions.assertTrue(deleteSize==0); //测试一下不包含删除的条数
    }

}
