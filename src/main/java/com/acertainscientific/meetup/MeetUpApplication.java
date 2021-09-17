package com.acertainscientific.meetup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.acertainscientific.meetup.mapper")

public class MeetUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetUpApplication.class, args);
	}

}
