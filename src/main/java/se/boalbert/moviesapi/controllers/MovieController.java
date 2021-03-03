package se.boalbert.moviesapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.dto.MovieRating;
import se.boalbert.moviesapi.services.Service;

import java.util.List;

@RestController
public class MovieController {

	private final Service service;

	public MovieController(Service service) {
		this.service = service;
	}

	@GetMapping("/movies/{id}")
	public MovieDto one(@PathVariable long id) {

		return service.getOne(id)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
								"MovieId" + id + " not found."));
	}

	@GetMapping("/movies")
	public List<MovieDto> all() {
		return service.getAllMovies();
	}

	@PostMapping("/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public MovieDto create(@RequestBody MovieDto movieDto) {
		return service.createMovie(movieDto);
	}

	@DeleteMapping("/movies/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

	@PutMapping("/movies/{id}")
	public MovieDto replace(@PathVariable Long id, @RequestBody MovieDto movieDto) {
		return service.replace(id, movieDto);
	}

	@PatchMapping("/movies/{id}")
	public MovieDto update(@PathVariable long id, @RequestBody MovieRating movieRating) {
		return service.update(id, movieRating);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/movies/search")
	public List<MovieDto> findByTitleContains(@RequestParam(value = "title") String title) {
		return service.findAllByContainsTitle(title);
	}
}
