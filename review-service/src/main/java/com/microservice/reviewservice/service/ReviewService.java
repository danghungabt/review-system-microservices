package com.microservice.reviewservice.service;

import com.microservice.reviewservice.dto.ReviewRequest;
import com.microservice.reviewservice.dto.ReviewResponse;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void insert(ReviewRequest reviewRequest){
        Review review = new Review();
        review.setTitle(reviewRequest.getTitle());
        review.setContent(reviewRequest.getContent());
        review.setLinkImage(reviewRequest.getLinkImage());
        review.setCreatedBy(reviewRequest.getCreatedBy());
        review.setCategoryCode(reviewRequest.getCategoryCode());
        review.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        reviewRepository.save(review);
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
