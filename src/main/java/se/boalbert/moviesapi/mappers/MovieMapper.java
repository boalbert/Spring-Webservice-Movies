package se.boalbert.moviesapi.mappers;

import org.springframework.stereotype.Component;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.entity.MovieEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

	public MovieMapper() {
	}

	// findOne
	public Optional<MovieDto> mapp(Optional<MovieEntity> optionalMovie) {

		if (optionalMovie.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(mapp(optionalMovie.get()));

	}

	// Create, post, patch
	// 1. Receive a Movie object
	// 2. Fetch all the properties via getX
	// 3. Set there properties for the new object (Dto)
	public MovieDto mapp(MovieEntity movieEntity) {
		return new MovieDto(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getReleaseDate(), movieEntity.getRunTimeMins(), movieEntity.getImdbRating());
	}

	public MovieEntity mapp(MovieDto movieDto) {

		return new MovieEntity(movieDto.getId(), movieDto.getTitle(), movieDto.getReleaseDate(), movieDto.getRunTimeMins(), movieDto.getImdbRating());

	}


	// Get all
	// 1. Recieve a movie-list
	// The stream converts every Movie object - converts to Dto - saves in a new list
	public List<MovieDto> mapp(List<MovieEntity> all) {

		return all.stream()
				.map(this :: mapp)
//				.map(movie -> mapp(movie))
				.collect(Collectors.toList());
	}


}
