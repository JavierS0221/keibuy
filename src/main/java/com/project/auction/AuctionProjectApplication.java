package com.project.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:config.properties")
@PropertySource("classpath:mail.properties")
@PropertySource("classpath:mysql_config.properties")
public class AuctionProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuctionProjectApplication.class, args);

	}

}
