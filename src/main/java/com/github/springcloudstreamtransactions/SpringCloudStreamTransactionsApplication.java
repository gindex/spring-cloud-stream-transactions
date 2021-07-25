package com.github.springcloudstreamtransactions;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudStreamTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamTransactionsApplication.class, args);
	}


	@Bean
	public Function<String, String> function(Service service) {
		return personName -> service.process(personName);
	}
}