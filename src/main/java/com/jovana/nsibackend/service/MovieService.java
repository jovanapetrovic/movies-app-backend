package com.jovana.nsibackend.service;

import com.jovana.nsibackend.exception.ResourceNotFoundException;
import com.jovana.nsibackend.model.Category;
import com.jovana.nsibackend.model.Movie;
import com.jovana.nsibackend.repository.CategoryRepository;
import com.jovana.nsibackend.repository.MovieRepository;
import com.jovana.nsibackend.resource.MovieRequest;
import com.jovana.nsibackend.resource.MovieResponse;
import com.jovana.nsibackend.resource.UpdateMovieRequest;
import com.jovana.nsibackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by jovana on 06.11.2018
 */
@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Set<MovieResponse> getWatchedMovies(UserPrincipal currentUser) {
        return movieRepository.findByCreatedBy(currentUser.getId());
    }

    public MovieResponse getMovie(Long movieId) {
        return movieRepository.findMovieById(movieId);
    }

    public Movie createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();

        Category category = categoryRepository.findByName(movieRequest.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", movieRequest.getCategoryName()));

        movie.setName(movieRequest.getName());
        movie.setYear(movieRequest.getYear());
        movie.setCategory(category);
        movie.setRating(movieRequest.getRating());
        movie.setComment(movieRequest.getComment());

        return movieRepository.save(movie);
    }

    public Movie updateMovie(UpdateMovieRequest movieRequest, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

        movie.setRating(movieRequest.getRating());
        movie.setComment(movieRequest.getComment());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

}
