package com.example.jpa.demo.service;

import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<UserInfo> getTop2UserInfo() {
		return userInfoRepository.findAll();
	}

	@Override
	public Optional<UserInfo> loadOne(Long id) {
		return userInfoRepository.findById(id);
	}

	/**
	 * 我们把逻辑封装在service方法里面，方法名字语义要清晰，就是说这个方法我们会取UserInfo的信息和Address的信息
	 *
	 * @param id
	 */
	@Override
	@Transactional
	public UserInfoDto getUserInfoAndAddress(Long id) {
		UserInfo u1 =  userInfoRepository.findById(id).get();
		return UserInfoDto.builder().name(u1.getName()).addressList(u1.getAddressList()).build();//按照业务要求，需要什么返回什么就可以了，让实体在service层之外是不可见的
	}

	/**
	 * 自己实现一套 Batch fetch的逻辑
	 */
	@Override
	@Transactional
	public List<UserInfo> getAllUserWithAddress() {
		//先查出来所有的UserInfo信息
		List<UserInfo> userInfos = userInfoRepository.findAll();
//		//再查出来上的的userInfos里面的所有userId列表，再查询出来上面的查询结果所对应的所有Address信息
//		List<Address> addresses = addressRepository.findByUserIdIn(userInfos.stream().map(userInfo -> userInfo.getId()).collect(Collectors.toList()));
//		//我们自己再写一个转化逻辑，把各自user info的address信息放置到响应的UserInfo实例里面；
//		Map<Long,List<Address>> addressMaps = addresses
//				.stream()
//				.collect(Collectors.groupingBy(Address::getUserId));//里面Map结构方便获取
//		return userInfos.stream().map(userInfo -> {
//			 userInfo.setAddressList(addressMaps.get(userInfo.getId()));
//			 return userInfo;
//		}).collect(Collectors.toList());
		return userInfos;
	}
}
