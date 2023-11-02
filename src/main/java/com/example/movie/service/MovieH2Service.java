package com.example.movie.service;

import com.example.movie.model.Movie;
import com.example.movie.model.MovieRowMapper;
import com.example.movie.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class MovieH2Service implements MovieRepository {
	@Autowired
	private JdbcTemplate db;

	@Override
	public ArrayList<Movie> getMovies() {
		List<Movie> movieList = db.query("select * from movielist", new MovieRowMapper());
		return new ArrayList<>(movieList);
	}

	@Override
	public Movie getMovieById(int movieId) {
		try {
			return db.queryForObject("select * from movielist where movieid = ?", new MovieRowMapper(), movieId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Movie addMovie(Movie movie) {
		db.update("insert into movielist(moviename,leadactor) values(?,?)", movie.getMovieName(), movie.getLeadActor());
		return db.queryForObject("select * from movielist where moviename = ? and leadactor= ?", new MovieRowMapper(),
				movie.getMovieName(), movie.getLeadActor());
	}

	@Override
	public Movie updateMovie(Movie movie, int movieId) {
		if (movie.getMovieName() != null)
			db.update("update movielist set moviename = ?  where movieid = ?", movie.getMovieName(),
					movieId);
		if (movie.getLeadActor() != null)
			db.update("update movielist set leadactor = ?  where movieid = ?", movie.getLeadActor(),
					movieId);
		return getMovieById(movieId);
	}

	@Override
	public void deleteMovie(int movieId) {
		db.update("delete from movielist where movieid=?", movieId);

	}

}
