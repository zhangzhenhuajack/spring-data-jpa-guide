package com.example.jpa.demo;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Test
	@Rollback(false)
	public  void testSave() {
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					userRepository.findById(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					userRepository.findById(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	@BeforeAll
	public void beforeTest() {
		User user = new User();
		user.setName("jack");
		user.setId(1);
		Role role = new Role();
		role.setId(2);
		role.setName("role1");
		user.setRoles(Lists.newArrayList(role));
		user.getRoles().add(new Role(3,"jack_role2"));
		userRepository.save(user);
	}
	@Test
	public void testFetch(){
		List<User> userList = userRepository.findAll();
		userList.forEach(u->{
			System.out.println("aaa"+u.getId());
			u.getRoles().forEach(r->{
				System.out.println(r.getName());
			});
		});
	}
}
