package com.challenge.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.challenge.school")
public class Application {

	public static void main(String[] args) {
		// TODO remove students on school search
		// TODO include students on school search by id
		SpringApplication.run(Application.class, args);
	}

}
