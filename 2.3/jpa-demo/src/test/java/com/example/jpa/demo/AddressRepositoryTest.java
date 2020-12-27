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
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
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
        List<Address> address1 = addressRepository.findAll();
        int deleteSize = address1.stream().filter(d->d.equals("shanghaiDeleted")).collect(Collectors.toList()).size();
        Assertions.assertTrue(deleteSize==0); //测试一下不包含删除的条数
    }

}
