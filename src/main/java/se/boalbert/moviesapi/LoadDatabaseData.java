package se.boalbert.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.boalbert.moviesapi.entity.MovieEntity;
import se.boalbert.moviesapi.repo.MovieRepo;

import java.time.LocalDate;

@Configuration
public class LoadDatabaseData {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabaseData.class);

	@Bean
	CommandLineRunner loadDatabase(MovieRepo movieRepo) {
		return args -> {

			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(1,"The Shawshank Redemption", LocalDate.of(1994,10,14),144,9.3)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(2,"The Godfather", LocalDate.of(1972,3,24),175,9.2)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(3,"The Godfather: Part II", LocalDate.of(1974,12,18),202,9.0)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(4,"The Dark Knight", LocalDate.of(2008, 7, 18),152,9.0)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(5,"12 Angry Men", LocalDate.of(1957, 4, 10),96,9.0)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(6,"Schindler's List", LocalDate.of(1994,2,4),195,8.9)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(7,"The Lords of the Rings: The Return of the Kind", LocalDate.of(2003,12,17),201,8.9)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(8,"Pulp Fiction", LocalDate.of(1994, 10, 14),154,8.9)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(9,"The Good, the Bad and the Ugly", LocalDate.of(1967, 12, 29),178,8.8)));
			LOGGER.info("Inserting sample data into DB: " + movieRepo.save(new MovieEntity(10,"The Lord of the Rings: The Fellowship of the Ring", LocalDate.of(2001, 12, 19),178,8.8)));

		};
	}
}
