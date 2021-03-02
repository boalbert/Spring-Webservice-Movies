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

	public Optional<MovieDto> mapp(Optional<MovieEntity> optionalMovie) {

		if (optionalMovie.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(mapp(optionalMovie.get()));

	}

	public MovieDto mapp(MovieEntity movieEntity) {
		return new MovieDto(movieEntity.getId(), movieEntity.getTitle(), movieEntity.getReleaseDate(), movieEntity.getRunTimeMins(), movieEntity.getImdbRating());
	}

	public MovieEntity mapp(MovieDto movieDto) {

		return new MovieEntity(movieDto.getId(), movieDto.getTitle(), movieDto.getReleaseDate(), movieDto.getRunTimeMins(), movieDto.getImdbRating());

	}

	public List<MovieDto> mapp(List<MovieEntity> all) {

		return all.stream()
				.map(this :: mapp)
				.collect(Collectors.toList());
	}


}
