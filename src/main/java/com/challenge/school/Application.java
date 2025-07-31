package com.challenge.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = "com.challenge.school")
public class Application {

	public static void main(String[] args) {
		// TODO Check student belong to school on delete
		// TODO check swagger validation why not show custom error message
		// TODO remove students on school search
		// TODO include students on school search by id
		// TODO proper error code message 409 conflict response
		SpringApplication.run(Application.class, args);
	}

}
