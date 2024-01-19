package com.microservice.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String name;
    private String email;
    private String website;
    private String content;
    private Long parentId;
    private Long reviewId;
}
