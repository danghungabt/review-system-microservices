package com.microservice.reviewservice.controller;

import com.microservice.reviewservice.dto.*;
import com.microservice.reviewservice.dto.response.BlogWithCategoryResponseModel;
import com.microservice.reviewservice.dto.response.MiniBlogWithCategoryResponseModel;
import com.microservice.reviewservice.service.ReviewService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public String insertComment(@RequestBody CommentRequest commentRequest){
        reviewService.insertComment(commentRequest);
        return "Inserted";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "category", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "category")
    @Retry(name = "category")
    public CompletableFuture<String> insert(@RequestBody ReviewRequest reviewRequest){
        return CompletableFuture.supplyAsync(() -> reviewService.insert(reviewRequest));
    }

    public CompletableFuture<String> fallbackMethod(ReviewRequest reviewRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please post review after some time!");
    }

    @GetMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> findAllComment(){
        return reviewService.findAllComment();
    }

    @GetMapping("/findOne")
    @ResponseStatus(HttpStatus.OK)
    public BlogWithCategoryResponseModel BlogWithCategoryResponseModel(@RequestParam String slugBlog){
        return reviewService.findOneBySlugClientPlus(slugBlog);
    }

    @GetMapping("/client")
    @ResponseStatus(HttpStatus.OK)
//    @CircuitBreaker(name = "category")
//    @TimeLimiter(name = "category")
//    @Retry(name = "category")
    public PagingModel<MiniBlogWithCategoryResponseModel> findAllClientWithPageablePlus(@RequestParam Map<String, Object> params,
                                                                                        @RequestParam int page){
        return reviewService.findAllClientWithPageablePlus(params, page);
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
