package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.model.Category;
import com.jovana.nsibackend.model.Movie;

/**
 * Created by jovana on 06.11.2018
 */
public class MovieResponse {

    private Long id;
    private String name;
    private Integer year;
    private Long categoryId;
    private String categoryName;
    private Integer rating;
    private String comment;

    public MovieResponse() {
    }

    public MovieResponse(Movie movie, Category category) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.year = movie.getYear();
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.rating = movie.getRating();
        this.comment = movie.getComment();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
