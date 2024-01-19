package com.microservice.categoryservice;

import com.microservice.categoryservice.model.Category;
import com.microservice.categoryservice.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class CategoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner loadData(CategoryRepository categoryRepository) {
        return args -> {
            Category category1 = new Category();
            category1.setName("Điện thoại");
            category1.setCode("dien-thoai");

            Category category2 = new Category();
            category2.setName("Thực tế ảo");
            category2.setCode("thuc-te-ao");

            Category category3 = new Category();
            category3.setName("Sự kiện");
            category3.setCode("su-kien");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
        };
    }*/
}