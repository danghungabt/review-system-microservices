package com.microservice.reviewservice.dto.response;

public class MiniBlogsResponseModel extends AbtractResponseModel {
    private String title;
    private String slugBlog;
    private Long categoryId;
    private String content;

    private String linkImage;

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

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
