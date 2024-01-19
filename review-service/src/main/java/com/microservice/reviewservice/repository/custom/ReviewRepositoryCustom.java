package com.microservice.reviewservice.repository.custom;

import com.microservice.reviewservice.dto.ReviewBuilder;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.paging.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> getAllReview();

    List<Review> findByCondition(ReviewBuilder reviewBuilder);

    List<Review> findByConditionWithPaging(ReviewBuilder reviewBuilder, Pageable pageable);
}
