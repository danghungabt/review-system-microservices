package com.microservice.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String linkImage;
    private String categoryCode;
    private String createdDate;
    private String slug;
}
