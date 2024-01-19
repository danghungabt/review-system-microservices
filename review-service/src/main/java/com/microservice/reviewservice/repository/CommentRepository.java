package com.microservice.reviewservice.repository;

import com.microservice.reviewservice.model.Comment;
import com.microservice.reviewservice.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> , CommentRepositoryCustom {
}
