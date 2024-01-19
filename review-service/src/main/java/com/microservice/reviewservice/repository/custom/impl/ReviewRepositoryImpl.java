package com.microservice.reviewservice.repository.custom.impl;

import com.microservice.reviewservice.dto.ReviewBuilder;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.paging.Pageable;
import com.microservice.reviewservice.repository.custom.ReviewRepositoryCustom;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Review> getAllReview() {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_review");
        Query query = entityManager.createNativeQuery(sql.toString(), Review.class);
        return query.getResultList();
    }

    @Override
    public List<Review> findByCondition(ReviewBuilder reviewBuilder) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_review");

        sql.append(" WHERE 1=1");
        sql = buildQuery(sql, reviewBuilder);

        Query query = entityManager.createNativeQuery(sql.toString(), Review.class);
        return query.getResultList();
    }

    private StringBuilder buildQuery(StringBuilder sql, ReviewBuilder reviewBuilder) {

        if(reviewBuilder.getKey() != null && reviewBuilder.getKey() != ""){
            sql.append(" AND title LIKE '%"+reviewBuilder.getKey()+"%'");
            sql.append(" OR content LIKE '%"+reviewBuilder.getKey()+"%'");
        }

        if(reviewBuilder.getCategoryCode() != null && reviewBuilder.getCategoryCode() != ""){
            sql.append(" AND category_code = '" + reviewBuilder.getCategoryCode() + "'");
        }

        return sql;
    }

    public StringBuilder pagingQuery(Pageable pageable, StringBuilder sql) {
        if (pageable.getSorter() != null && StringUtils.isNotBlank(pageable.getSorter().getSortName())
                && StringUtils.isNotBlank(pageable.getSorter().getSortBy())) {
            sql.append(" ORDER BY " + pageable.getSorter().getSortName() + " " + pageable.getSorter().getSortBy());
        }
        if (pageable.getOffset() != null && pageable.getLimit() != null) {
            sql.append(" OFFSET " + pageable.getOffset() + " LIMIT " + pageable.getLimit());
        }

        return sql;
    }

    @Override
    public List<Review> findByConditionWithPaging(ReviewBuilder reviewBuilder, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_review");

        sql.append(" WHERE 1=1");
        sql = buildQuery(sql, reviewBuilder);
        sql = pagingQuery(pageable, sql);

        Query query = entityManager.createNativeQuery(sql.toString(), Review.class);
        return query.getResultList();
    }
}
