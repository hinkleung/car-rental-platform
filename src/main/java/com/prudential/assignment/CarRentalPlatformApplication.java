package com.prudential.assignment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.prudential.assignment.repository.mapper")
public class CarRentalPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalPlatformApplication.class, args);
	}

}
