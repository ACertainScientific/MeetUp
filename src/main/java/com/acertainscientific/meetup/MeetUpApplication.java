package com.acertainscientific.meetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// try:)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MeetUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetUpApplication.class, args);
	}

}
