package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.util.Constants;

import javax.validation.constraints.*;

/**
 * Created by jovana on 06.11.2018
 */
public class UpdateMovieRequest {

    @NotNull
    @Min(Constants.MOVIE_RATING_MIN)
    @Max(Constants.MOVIE_RATING_MAX)
    private Integer rating;

    @NotBlank
    @Size(max = Constants.MOVIE_COMMENT_MAX_LENGTH)
    private String comment;

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
