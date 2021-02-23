package se.boalbert.moviesapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.dto.MovieRating;
import se.boalbert.moviesapi.services.Service;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

	private Service service;

	public MovieController(Service service) {
		this.service = service;
	}

	// Test written
	@GetMapping("/movies/{id}")
	public MovieDto one(@PathVariable long id) {

		return service.getOne(id)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
								"Id" + id + " not found."));
	}

	// Test written
	@GetMapping("/movies")
	public List<MovieDto> all() {
		return service.getAllMovies();
	}

	//TODO Write test for POST
	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public MovieDto create(@RequestBody MovieDto movieDto) {
		return service.createMovie(movieDto);
	}

	// TODO Write test for Delete
	@DeleteMapping
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	// TODO Write test for Put
	@PutMapping("/movies/{id}")
	public MovieDto replace(@PathVariable Long id, @RequestBody MovieDto movieDto) {
		return service.replace(id, movieDto);
	}

	// TODO Write test for Patch
	@PatchMapping("/movies/{id}")
	public MovieDto update(@PathVariable long id, @RequestBody MovieRating movieRating) {
		return service.update(id, movieRating);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/movies/search")
	@ResponseBody
	public Optional<MovieDto> findByTitle(@RequestParam(value="title") String title) {
		return service.findByTitle(title);
	}

}
