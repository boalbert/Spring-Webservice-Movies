package se.boalbert.moviesapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.services.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MvcTestMovies {

	@MockBean
	Service service;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void callingUrlMoviesWithId_ReturnOneMovieAsJson() throws Exception {

		when(service.getOne(1L))
				.thenReturn(Optional.of(new MovieDto(1, "Title", LocalDate.of(2020, 1, 1), 123, 5.0)));


		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get("/movies/1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void callingUrlMoviesWithInvalidId_ThrowNotFoundException() throws Exception {

		when(service.getOne(1L))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get("/movies/1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();


		assertThat(result.getResolvedException().getMessage()).isEqualTo("404 NOT_FOUND");
		assertThat(result.getResolvedException().getMessage()).isNotNull();
	}

	@Test
	void callingAllMovies_ReturnListOfAllMovies() throws Exception {
		when(service.getAllMovies())
				.thenReturn(List.of(
						new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0),
						new MovieDto(2, "Title", LocalDate.of(1999, 1, 1), 123, 5.0)));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get("/movies")
						.accept(MediaType.APPLICATION_JSON))
						.andReturn();

		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void whenValidMoviePosted_thenReturnsHttpStatusCreated() throws Exception {
		MovieDto newMovieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);


		mvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(newMovieDto)))
				.andExpect(status().isCreated());
	}

	@Test
	void whenNullValue_thenReturns400() throws Exception {
		MovieDto newMovieDto = new MovieDto(null, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);

		mvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(newMovieDto)))
				.andExpect(status().isBadRequest());
	}



	//	@Test
	//	public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
	//			throws Exception {
	//
	//		MovieDto newMovieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);
	//
	//		given(service.createMovie(newMovieDto)).willReturn(newMovieDto);
	//
	//		mvc.perform(MockMvcRequestBuilders.post("/movies")
	//				.contentType(MediaType.APPLICATION_JSON))
	//				.andExpect(status().isOk());
	//	}
}
