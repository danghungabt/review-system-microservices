package com.microservice.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String name;
    private String email;
    private String website;
    private String content;
    private Long parentId;
    private Long reviewId;
}
