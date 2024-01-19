package com.microservice.reviewservice.repository.custom;

import com.microservice.reviewservice.model.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findCommentByReviewId(Long reviewId);
}
