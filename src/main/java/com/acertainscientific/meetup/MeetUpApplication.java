package com.acertainscientific.meetup;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.acertainscientific.meetup.mapper")
@OpenAPIDefinition(info = @Info(title = "MeetUp API", version = "2.0", description = "MeetUp"))

public class MeetUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetUpApplication.class, args);
	}

}
