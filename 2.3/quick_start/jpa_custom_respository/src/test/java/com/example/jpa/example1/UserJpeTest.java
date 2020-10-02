package com.example.jpa.example1;

import com.example.jpa.example1.spe.MySpecification;
import com.example.jpa.example1.spe.Operator;
import com.example.jpa.example1.spe.SearchCriteria;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserJpeTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    private Date now = new Date();

    /**
     * 提前创建一些数据
     */
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
    public void testSPE() {
        //模拟请求参数
        User userQuery = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .addresses(Lists.newArrayList(UserAddress.builder().address("shanghai").build()))
                .build();
        Instant beginCreateDate = Instant.now().plus(-2, ChronoUnit.HOURS);
        Instant endCreateDate = Instant.now().plus(1, ChronoUnit.HOURS);

        //利用Specification进行查询
        Page<User> users = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> ps = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(userQuery.getName())) {
                    //我们模仿一下like查询，根据name模糊查询
                    ps.add(cb.like(root.get("name"),"%" +userQuery.getName()+"%"));
                }
                if (userQuery.getSex()!=null){
                    //equal查询条件，这里需要注意，直接传递的是枚举
                    ps.add(cb.equal(root.get("sex"),userQuery.getSex()));
                }
                if (userQuery.getAge()!=null){
                    //greaterThan大于等于查询条件
                    ps.add(cb.greaterThan(root.get("age"),userQuery.getAge()));
                }
                if (beginCreateDate!=null&&endCreateDate!=null){
                    //根据时间区间去查询创建
                    ps.add(cb.between(root.get("createDate"),beginCreateDate,endCreateDate));
                }
                if (!ObjectUtils.isEmpty(userQuery.getAddresses())) {
                    //联表查询，利用root的join方法，根据关联关系表里面的字段进行查询。
                    ps.add(cb.in(root.join("addresses").get("address")).value(userQuery.getAddresses().stream().map(a->a.getAddress()).collect(Collectors.toList())));
                }
                return query.where(ps.toArray(new Predicate[ps.size()])).groupBy(root.get("age")).getRestriction();
            }
        }, PageRequest.of(0, 2));
        System.out.println(users);
    }

    /**
     * 测试自定义的Specification语法
     */
    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        MySpecification<User> name =
                new MySpecification<User>(new SearchCriteria("name", Operator.LK, "jack"));
        MySpecification<User> age =
                new MySpecification<User>(new SearchCriteria("age", Operator.GT, 2));

        List<User> results = userRepository.findAll(Specification.where(name).and(age));

        System.out.println(results.get(0).getName());
    }
}
