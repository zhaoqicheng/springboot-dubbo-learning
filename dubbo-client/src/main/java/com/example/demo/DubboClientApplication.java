package com.example.demo;

import com.example.demo.dubbo.CityDubboConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DubboClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DubboClientApplication.class, args);
		CityDubboConsumerService cityService = run.getBean(CityDubboConsumerService.class);
		cityService.printCity();
	}
}
