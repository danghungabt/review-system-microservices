package com.microservice.reviewservice.service;

import brave.Span;
import brave.Tracer;
import com.microservice.reviewservice.dto.*;
import com.microservice.reviewservice.dto.response.CategoriesResponseModel;
import com.microservice.reviewservice.dto.response.MiniBlogWithCategoryResponseModel;
import com.microservice.reviewservice.dto.response.MiniBlogsResponseModel;
import com.microservice.reviewservice.model.Comment;
import com.microservice.reviewservice.model.Review;
import com.microservice.reviewservice.paging.PageRequest;
import com.microservice.reviewservice.paging.Pageable;
import com.microservice.reviewservice.repository.CommentRepository;
import com.microservice.reviewservice.repository.ReviewRepository;
import com.microservice.reviewservice.utils.MapUtils;
import com.microservice.reviewservice.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public String insert(ReviewRequest reviewRequest) {
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
            if (categoryResponse != null) {
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

    public List<ReviewResponse> findAll() {
        return reviewRepository.getAllReview().stream().map(this::mapToReviewResponse).toList();
    }

    public List<CommentResponse> findAllComment() {
        return commentRepository.findAll().stream().map(this::mapToCommentResponse).toList();
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        return  CommentResponse.builder()
                .name(comment.getName())
                .email(comment.getEmail())
                .reviewId(comment.getReview().getId())
                .parentId(comment.getParentId())
                .content(comment.getContent())
                .website(comment.getWebsite())
                .id(comment.getId())
                .build();
    }

    public void insertComment(CommentRequest commentRequest) {
        Review review = reviewRepository.findById(commentRequest.getReviewId()).isPresent() ?
                reviewRepository.findById(commentRequest.getReviewId()).get()
                : null;

        Comment comment = new Comment();
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setContent(commentRequest.getContent());
        comment.setParentId(commentRequest.getParentId());
        comment.setWebsite(commentRequest.getWebsite());
        comment.setReview(review);

        commentRepository.save(comment);
    }


    public PagingModel<MiniBlogWithCategoryResponseModel> findAllClientWithPageablePlus(Map<String, Object> params,int page) {

        ReviewBuilder reviewBuilder  = ReviewBuilder.builder().key(MapUtils.getobject(params, "key", String.class))
                .categoryCode(MapUtils.getobject(params, "categoryCode", String.class)).build();
        Pageable pageable = new PageRequest(page, 7);
        PagingModel<MiniBlogWithCategoryResponseModel> result = new PagingModel<MiniBlogWithCategoryResponseModel>();
        result.setPage(page);
        result.setPageSize(7);
        result.setTotalItem(reviewRepository.findByCondition(reviewBuilder).size());
        result.setTotalPage((int) Math.ceil((double) result.getTotalItem() / result.getPageSize()));

        List<Review> reviews = reviewRepository.findByConditionWithPaging( reviewBuilder, pageable);

        List<MiniBlogWithCategoryResponseModel> miniBlogWithCategoryResponseModels = new ArrayList<>();

        Span inventoryServiceLookup = tracer.nextSpan().name("CategoryServiceLookup");
        log.info("Begin");
        try (Tracer.SpanInScope isLookup = tracer.withSpanInScope(inventoryServiceLookup.start())) {
            inventoryServiceLookup.tag("call", "category-service");
            log.info("Begin 2");

            CategoryResponse[] categoryResponses = webClientBuilder.build().get()
                    .uri("http://category-service/api/category/")
                    .retrieve()
                    .bodyToMono(CategoryResponse[].class)
                    .block();

            log.info("Begin 3");
            if (categoryResponses != null && categoryResponses.length > 0) {
                for (Review review : reviews){
                    int totalComment = commentRepository.findCommentByReviewId(review.getId()).size();
                    for (CategoryResponse categoryResponse : categoryResponses){
                        if(review.getCategoryCode().equals(categoryResponse.getCode())){
                            MiniBlogWithCategoryResponseModel temp = new MiniBlogWithCategoryResponseModel();
                            MiniBlogsResponseModel temp2 = new MiniBlogsResponseModel();
                            CategoriesResponseModel temp3 = new CategoriesResponseModel();
                            temp2.setId(review.getId());
                            temp2.setSlugBlog(review.getId().toString());
                            temp2.setTitle(review.getTitle());
                            temp2.setContent(StringUtils.customSubstring(review.getContent(), 100));
                            temp2.setCategoryId(categoryResponse.getId());
                            temp2.setLinkImage(review.getLinkImage());
                            temp.setBlogsResponseModel(temp2);
                            temp3.setId(categoryResponse.getId());
                            temp3.setCategory(categoryResponse.getName());
                            temp3.setSlugCategory(categoryResponse.getCode());
                            temp.setCategoriesResponseModel(temp3);
                            temp.setTotalComment(totalComment);
                            miniBlogWithCategoryResponseModels.add(temp);
                        }
                    }
                }

                result.setListResult(miniBlogWithCategoryResponseModels);
            } else {
                throw new IllegalArgumentException("Category is not exist, please try again later");
            }
        } finally {
            inventoryServiceLookup.flush();
        }


        return result;
    }

}
