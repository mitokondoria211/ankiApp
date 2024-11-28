package com.example.ankiapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mapper")
public class AnkiappApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnkiappApplication.class, args);
	}

}
