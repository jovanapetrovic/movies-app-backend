package com.jovana.nsibackend.controller;

import com.jovana.nsibackend.model.Movie;
import com.jovana.nsibackend.resource.ApiResponse;
import com.jovana.nsibackend.resource.MovieRequest;
import com.jovana.nsibackend.resource.MovieResponse;
import com.jovana.nsibackend.resource.UpdateMovieRequest;
import com.jovana.nsibackend.security.CurrentUser;
import com.jovana.nsibackend.security.UserPrincipal;
import com.jovana.nsibackend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

/**
 * Created by jovana on 06.11.2018
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private static final String MOVIE_ID_PLACEHOLDER = "/{movieId}";

    @Autowired
    private MovieService movieService;

    @GetMapping
    public Set<MovieResponse> getWatchedMovies(@CurrentUser UserPrincipal currentUser) {
        return movieService.getWatchedMovies(currentUser);
    }

    @GetMapping("/{movieId}")
    public MovieResponse getMovie(@PathVariable Long movieId) {
        return movieService.getMovie(movieId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        Movie movie = movieService.createMovie(movieRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(MOVIE_ID_PLACEHOLDER).buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Movie created successfully", movie.getId()));
    }

    @PutMapping("/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> updateMovie(@Valid @RequestBody UpdateMovieRequest movieRequest, @PathVariable Long movieId) {
        Movie movie = movieService.updateMovie(movieRequest, movieId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(MOVIE_ID_PLACEHOLDER).buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Movie updated successfully"));
    }

    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(MOVIE_ID_PLACEHOLDER).buildAndExpand("").toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Movie deleted successfully"));
    }

}
