package com.microservice.reviewservice.repository.custom.impl;

import com.microservice.reviewservice.model.Comment;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.repository.custom.CommentRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findCommentByReviewId(Long reviewId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_comment WHERE reviewid = " + reviewId);
        Query query = entityManager.createNativeQuery(sql.toString(), Comment.class);
        return query.getResultList();
    }
}
