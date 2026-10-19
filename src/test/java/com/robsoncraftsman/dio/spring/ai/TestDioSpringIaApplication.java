package com.robsoncraftsman.dio.spring.ai;

import org.springframework.boot.SpringApplication;

public class TestDioSpringIaApplication {

	public static void main(String[] args) {
		SpringApplication.from(DioSpringIaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
