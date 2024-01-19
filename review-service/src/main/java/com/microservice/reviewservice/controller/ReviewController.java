package com.microservice.reviewservice.controller;

import com.microservice.reviewservice.dto.ReviewRequest;
import com.microservice.reviewservice.dto.ReviewResponse;
import com.microservice.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insert(@RequestBody ReviewRequest reviewRequest){
        reviewService.insert(reviewRequest);
        return "Insert successfully";
    }

    @GetMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse findOndById(@PathVariable Long reviewId){
        return reviewService.findOneById(reviewId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> findAll(){
        return reviewService.findAll();
    }
}
