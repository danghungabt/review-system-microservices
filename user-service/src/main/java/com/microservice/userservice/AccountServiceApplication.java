package com.microservice.userservice;

import com.microservice.userservice.model.Role;
import com.microservice.userservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}


	/*@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository) {
		return args -> {
			Role role1 = new Role();
			role1.setCode("ADMIN");
			role1.setName("Admin");

			Role role2 = new Role();
			role2.setCode("USER");
			role2.setName("User");

			roleRepository.save(role1);
			roleRepository.save(role2);
		};
	}*/

}
