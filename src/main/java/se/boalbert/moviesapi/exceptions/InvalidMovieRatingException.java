package se.boalbert.moviesapi.exceptions;

public class InvalidMovieRatingException extends RuntimeException {

	public InvalidMovieRatingException(Long id) {
		super("Invalid rating for id: " + id + ", - should be between '1.0' and '10.0'.");
	}
}