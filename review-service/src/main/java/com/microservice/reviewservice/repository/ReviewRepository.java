package com.microservice.reviewservice.repository;

import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
