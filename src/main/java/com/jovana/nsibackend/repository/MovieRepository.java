package com.jovana.nsibackend.repository;

import com.jovana.nsibackend.model.Movie;
import com.jovana.nsibackend.resource.MovieResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by jovana on 06.11.2018
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select new com.jovana.nsibackend.resource.MovieResponse(m, c) " +
            "from Movie m, Category c where m.id = :movieId and m.category.id = c.id")
    MovieResponse findMovieById(@Param("movieId") Long movieId);

    @Query("select new com.jovana.nsibackend.resource.MovieResponse(m, c) " +
            "from Movie m, Category c where m.createdBy = :userId and m.category.id = c.id")
    Set<MovieResponse> findByCreatedBy(@Param("userId") Long userId);

}
