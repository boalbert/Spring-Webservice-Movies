package se.boalbert.moviesapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.boalbert.moviesapi.entity.MovieEntity;

import java.util.List;
import java.util.Optional;


public interface MovieRepo extends JpaRepository<MovieEntity, Long> {

	List<MovieEntity> findAllByTitleContains(String title);

	Optional<MovieEntity> findById(Long id);

	List<MovieEntity> findAll();

}
