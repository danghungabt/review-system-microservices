package com.microservice.categoryservice.service;

import com.microservice.categoryservice.dto.CategoryResponse;
import com.microservice.categoryservice.model.Category;
import com.microservice.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll().stream().map(this::maoToCategoryResponse).toList();
    }

    public CategoryResponse findOneByCode(String code){
        return categoryRepository.findFirstByCode(code).isPresent() ? null
                : maoToCategoryResponse(categoryRepository.findFirstByCode(code).get());
    }

    private CategoryResponse maoToCategoryResponse(Category item) {
        return CategoryResponse.builder()
                .id(item.getId())
                .code(item.getCode())
                .name(item.getName())
                .build();
    }



}
