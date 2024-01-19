package com.microservice.reviewservice.dto.response;

public class MiniBlogWithCategoryResponseModel {

    private MiniBlogsResponseModel blogsResponseModel;
    private CategoriesResponseModel categoriesResponseModel;
    private Integer totalComment;

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

    public MiniBlogsResponseModel getBlogsResponseModel() {
        return blogsResponseModel;
    }

    public void setBlogsResponseModel(MiniBlogsResponseModel blogsResponseModel) {
        this.blogsResponseModel = blogsResponseModel;
    }
}
