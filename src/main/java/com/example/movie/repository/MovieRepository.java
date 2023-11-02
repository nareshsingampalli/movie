package com.example.movie.repository;

import java.util.*;
import com.example.movie.model.Movie;

public interface MovieRepository {
    ArrayList<Movie> getMovies();

    Movie getMovieById(int movieId);

    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie, int movieId);

    void deleteMovie(int movieId);
}