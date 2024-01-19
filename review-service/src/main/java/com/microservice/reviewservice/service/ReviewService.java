package com.microservice.reviewservice.service;

import brave.Span;
import brave.Tracer;
import com.microservice.reviewservice.dto.CategoryResponse;
import com.microservice.reviewservice.dto.ReviewRequest;
import com.microservice.reviewservice.dto.ReviewResponse;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public String insert(ReviewRequest reviewRequest){
        Review review = new Review();
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setLinkImage(reviewRequest.getLinkImage());
        review.setCreatedBy(reviewRequest.getCreatedBy());
        review.setCategoryCode(reviewRequest.getCategoryCode());
        review.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Span inventoryServiceLookup = tracer.nextSpan().name("CategoryServiceLookup");
        log.info("Begin");
        try (Tracer.SpanInScope isLookup = tracer.withSpanInScope(inventoryServiceLookup.start())) {
            inventoryServiceLookup.tag("call", "category-service");
            log.info("Begin 2");

            CategoryResponse categoryResponse = webClientBuilder.build().get()
                    .uri("http://category-service/api/category/{categoryCode}", reviewRequest.getCategoryCode())
                    .retrieve()
                    .bodyToMono(CategoryResponse.class)
                    .block();

            log.info("Begin 3");
            log.info(categoryResponse.getCode());
            if(categoryResponse != null) {
                reviewRepository.save(review);
                return "Review Was Posted Successfully";
            } else {
                throw new IllegalArgumentException("Category is not exist, please try again later");
            }
        } finally {
            inventoryServiceLookup.flush();
        }
    }

    public ReviewResponse findOneById(Long id) {
        return mapToReviewResponse(reviewRepository.findById(id).get());
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = review.getCreatedDate().toLocalDateTime();
        return ReviewResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .createdBy(review.getCreatedBy())
                .linkImage(review.getLinkImage())
                .categoryCode(review.getCategoryCode())
                .slug(review.getId().toString())
                .createdDate(localDateTime.format(formatter))
                .build();
    }

    public List<ReviewResponse> findAll(){
        return reviewRepository.findAll().stream().map(this::mapToReviewResponse).toList();
    }
}
