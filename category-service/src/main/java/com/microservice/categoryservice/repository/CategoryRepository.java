package com.microservice.categoryservice.repository;

import com.microservice.categoryservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findFirstByCode(String code);
    List<Category> findRolesByCode(String code);
}
