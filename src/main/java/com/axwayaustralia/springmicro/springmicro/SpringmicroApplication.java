package com.axwayaustralia.springmicro.springmicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.axwayaustralia")
public class SpringmicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmicroApplication.class, args);
	}

}
