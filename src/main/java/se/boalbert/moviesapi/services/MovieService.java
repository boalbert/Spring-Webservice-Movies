package se.boalbert.moviesapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.dto.MovieRating;
import se.boalbert.moviesapi.entity.MovieEntity;
import se.boalbert.moviesapi.mappers.MovieMapper;
import se.boalbert.moviesapi.repo.MovieRepo;

import java.util.List;
import java.util.Optional;

@Component
public class MovieService implements Service {

	// These will be used to convert Movie to Dto / etc
	private MovieMapper movieMapper;
	// This will be used to talk to the database
	private MovieRepo movieRepo;

	public MovieService(MovieMapper movieMapper, MovieRepo movieRepo) {
		this.movieMapper = movieMapper;
		this.movieRepo = movieRepo;
	}

	@Override
	public MovieDto createMovie(MovieDto movieDto) {
		if (movieDto.getTitle().isEmpty())
			throw new RuntimeException();
		return movieMapper.mapp(movieRepo.save(movieMapper.mapp(movieDto)));
	}

	@Override
	public List<MovieDto> getAllMovies() {
		return movieMapper.mapp(movieRepo.findAll());
	}

	@Override
	public Optional<MovieDto> getOne(Long id) {
		return movieMapper.mapp(movieRepo.findById(id));
	}

	@Override
	public void delete(Long id) {
		movieRepo.deleteById(id);
	}

	@Override
	public MovieDto replace(Long id, MovieDto movieDto) {
		Optional<MovieEntity> movieEntity = movieRepo.findById(id);

		if (movieEntity.isPresent()) {
			MovieEntity updatedMovieEntity = movieEntity.get();

			updatedMovieEntity.setTitle(movieDto.getTitle());
			updatedMovieEntity.setReleaseDate(movieDto.getReleaseDate());
			updatedMovieEntity.setRunTimeMins(movieDto.getRunTimeMins());
			updatedMovieEntity.setImdbRating(movieDto.getImdbRating());

			return movieMapper.mapp(movieRepo.save(updatedMovieEntity));

		} else
			throw new
					ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found.");

	}

	@Override
	public MovieDto update(long id, MovieRating movieRating) {
		Optional<MovieEntity> movieEntity = movieRepo.findById(id);

		if (movieEntity.isPresent()) {
			MovieEntity updatedMovieEntity = movieEntity.get();

			if (movieRating != null && !(movieRating.imdbRating >= 1.0 && movieRating.imdbRating <= 10.0))
				//				throw new InvalidMovieRatingException(id); // TODO Return response as Json
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid IMDB Rating: " + movieRating.imdbRating + ". Accepts '1.0' - '10.0'."); // TODO Return response as Json

			if (movieRating != null && (movieRating.imdbRating >= 1.0 && movieRating.imdbRating <= 10.0))
				updatedMovieEntity.setImdbRating(movieRating.imdbRating);
			return movieMapper.mapp(movieRepo.save(updatedMovieEntity));

		} else {
			throw new
					ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found.");
		}

	}

	@Override
	public Optional<MovieDto> findByTitle(String title) {
		Optional<MovieEntity> movieEntity = movieRepo.findByTitle(title);
		if(movieEntity.isPresent())
			return movieMapper.mapp(movieRepo.findByTitle(title));
		else {
			throw new
					ResponseStatusException(HttpStatus.NOT_FOUND, "Title: " + title + " not found.");
		}
	}
}

