package se.boalbert.moviesapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidMovieRatingExceptionAdvice {


	@ExceptionHandler(InvalidMovieRatingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	String invalidMovieRatingHandler(InvalidMovieRatingException e) {
		return e.getMessage();
	}
}
