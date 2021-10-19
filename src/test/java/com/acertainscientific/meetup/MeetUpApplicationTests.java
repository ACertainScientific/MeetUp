package com.acertainscientific.meetup;

import ch.qos.logback.core.pattern.color.RedCompositeConverter;
import com.acertainscientific.meetup.model.UserModel;
import com.acertainscientific.meetup.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
class MeetUpApplicationTests {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ModelMapper modelMapper;
	@Test
	void contextLoads() {
	}

	@Test
	void redis() {
		UserModel a= new UserModel();
		a.setId(1);
		a.setPassword("1");
		a.setCreatedAt(1);
		a.setDeletedAt(1);
		a.setIsDeleted(1);
		a.setEmail("aa");
		a.setUserName("a");
		redisUtil.set("User:"+a.getId().toString(),a);

		UserModel b=modelMapper.map(redisUtil.get("User:1"),UserModel.class);
		log.info(b.toString());
	}


}
