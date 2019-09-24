package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.util.Constants;

import javax.validation.constraints.*;

/**
 * Created by jovana on 06.11.2018
 */
public class MovieRequest {

    @NotBlank
    @Size(max = Constants.MOVIE_NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Min(Constants.MOVIE_YEAR_MIN)
    @Max(Constants.MOVIE_YEAR_MAX)
    private Integer year;

    @NotBlank
    private String categoryName;

    @NotNull
    @Min(Constants.MOVIE_RATING_MIN)
    @Max(Constants.MOVIE_RATING_MAX)
    private Integer rating;

    @NotBlank
    @Size(max = Constants.MOVIE_COMMENT_MAX_LENGTH)
    private String comment;

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
