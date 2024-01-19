package com.microservice.reviewservice.dto.response;

public class BlogsResponseModel extends AbtractResponseModel {
    private String title;
    private String slugBlog;
    private String dateSubmitted;
    private Long categoryId;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlugBlog() {
        return slugBlog;
    }

    public void setSlugBlog(String slugBlog) {
        this.slugBlog = slugBlog;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
