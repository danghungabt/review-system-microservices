package com.microservice.reviewservice.repository;

import com.microservice.reviewservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
