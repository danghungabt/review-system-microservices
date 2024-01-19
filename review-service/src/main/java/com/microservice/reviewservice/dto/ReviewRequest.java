package com.microservice.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private String title;
    private String content;
    private String createdBy;
    private String linkImage;
    private String categoryCode;
}
