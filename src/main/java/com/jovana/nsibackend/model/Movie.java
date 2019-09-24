package com.jovana.nsibackend.model;

import com.jovana.nsibackend.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by jovana on 06.11.2018
 */
@Entity
@Table(name = "movies")
public class Movie extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = Constants.MOVIE_NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Min(Constants.MOVIE_YEAR_MIN)
    @Max(Constants.MOVIE_YEAR_MAX)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Min(Constants.MOVIE_RATING_MIN)
    @Max(Constants.MOVIE_RATING_MAX)
    private Integer rating;

    @NotNull
    @Size(max = Constants.MOVIE_COMMENT_MAX_LENGTH)
    private String comment;

    public Movie() {}

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
