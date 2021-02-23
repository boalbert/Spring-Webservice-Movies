package se.boalbert.moviesapi.services;

import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.dto.MovieRating;

import java.util.List;
import java.util.Optional;

public interface Service {

	MovieDto createMovie(MovieDto movieDto);

	List<MovieDto> getAllMovies();

	Optional<MovieDto> getOne(Long id);

	void delete(Long id);

	MovieDto replace(Long id, MovieDto movieDto);

	MovieDto update(long id, MovieRating movieRating);

	Optional<MovieDto> findByTitle(String title);
}
