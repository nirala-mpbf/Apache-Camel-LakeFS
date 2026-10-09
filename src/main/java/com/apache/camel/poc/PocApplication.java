package com.apache.camel.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
		System.out.println("Up and Running");
	}

}
