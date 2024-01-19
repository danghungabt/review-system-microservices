package com.microservice.reviewservice.dto.response;

public class BlogWithCategoryResponseModel {

    private BlogsResponseModel blogsResponseModel;
    private CategoriesResponseModel categoriesResponseModel;
    private Integer totalComment;

    public BlogsResponseModel getBlogsResponseModel() {
        return blogsResponseModel;
    }

    public void setBlogsResponseModel(BlogsResponseModel blogsResponseModel) {
        this.blogsResponseModel = blogsResponseModel;
    }

    public CategoriesResponseModel getCategoriesResponseModel() {
        return categoriesResponseModel;
    }

    public void setCategoriesResponseModel(CategoriesResponseModel categoriesResponseModel) {
        this.categoriesResponseModel = categoriesResponseModel;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}
