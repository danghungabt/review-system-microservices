package com.microservice.categoryservice.service;

import com.microservice.categoryservice.dto.CategoryRequest;
import com.microservice.categoryservice.dto.CategoryResponse;
import com.microservice.categoryservice.model.Category;
import com.microservice.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void insert(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setCode(categoryRequest.getCode());
        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
    }

    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll().stream().map(this::maoToCategoryResponse).toList();
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public CategoryResponse findOneByCode(String code){
        log.info("Checking Category");
        return categoryRepository.findFirstByCode(code).isPresent() ?
                maoToCategoryResponse(categoryRepository.findFirstByCode(code).get())
                : null;
    }

    private CategoryResponse maoToCategoryResponse(Category item) {
        return CategoryResponse.builder()
                .id(item.getId())
                .slugCategory(item.getCode())
                .category(item.getName())
                .build();
    }



}
