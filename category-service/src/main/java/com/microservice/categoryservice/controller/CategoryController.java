package com.microservice.categoryservice.controller;

import com.microservice.categoryservice.dto.CategoryRequest;
import com.microservice.categoryservice.dto.CategoryResponse;
import com.microservice.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insert(@RequestBody CategoryRequest categoryRequest){
        categoryService.insert(categoryRequest);
        return "Insert successfully";
    }

    @GetMapping("/{categoryCode}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse findOndByCode(@PathVariable(value = "categoryCode") String categoryCode){
        return categoryService.findOneByCode(categoryCode);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }
}
