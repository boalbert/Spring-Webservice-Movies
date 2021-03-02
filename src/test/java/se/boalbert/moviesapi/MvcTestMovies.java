package se.boalbert.moviesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;
import se.boalbert.moviesapi.controllers.MovieController;
import se.boalbert.moviesapi.dto.MovieDto;
import se.boalbert.moviesapi.dto.MovieRating;
import se.boalbert.moviesapi.services.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MvcTestMovies {

	@Autowired
	public ObjectMapper objectMapper;
	@MockBean
	Service service;
	@Autowired
	private MockMvc mvc;

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
	void callingUrlMoviesWithInvalidId_Throw404NotFoundException() throws Exception {

		when(service.getOne(1L))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

		mvc.perform(
				MockMvcRequestBuilders.get("/movies/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void callingUrlAllMovies_ReturnListOfAllMovies() throws Exception {
		when(service.getAllMovies())
				.thenReturn(List.of(
						new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0),
						new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0),
						new MovieDto(2, "Title", LocalDate.of(1999, 1, 1), 123, 5.0)));

		mvc.perform(
				MockMvcRequestBuilders.get("/movies")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void whenValidMoviePosted_thenReturnsHttpStatusCreated() throws Exception {
		MovieDto newMovieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);

		mvc.perform(post("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newMovieDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void postingMovieShouldReturnMovieInResponseBody() throws Exception {

		MovieDto movieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);

		when(service.createMovie(any(MovieDto.class))).thenReturn(movieDto);

		RequestBuilder requestBuilder = post("/movies")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(movieDto))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		assertThat(result.getResponse().getContentAsByteArray()).isEqualTo(objectMapper.writeValueAsBytes(movieDto));
	}

	@Test
	void replaceMovieWith_Valid_DetailsWithPut_ReturnsResponseOK() throws Exception {

		MovieDto movieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);

		mvc.perform(put("/movies/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(
						movieDto))
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(movieDto.getTitle()));
	}

	@Test
	void patchMovieWithValidRating_ReturnsResponseOK() throws Exception {

		MovieDto movieDto = new MovieDto(1, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);


		when(service.update(eq(1L), any(MovieRating.class))).thenReturn(
				movieDto);

		MovieRating movieRating = new MovieRating();
		movieRating.setImdbRating(6.0);

		mvc.perform(patch("/movies/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(movieRating))
		).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.imdbRating").value(movieDto.getImdbRating()));
	}

	@Test
	void searchForMovieWithCorrectTitle_ReturnMovieInResponseBody() throws Exception {

		MovieDto movieDto = new MovieDto(1L, "MovieTitle", LocalDate.of(1999, 1, 1), 123, 5.0);


		when(service.findAllByContainsTitle("Title")).thenReturn(List.of(
				new MovieDto(1L, "Title", LocalDate.of(1999, 1, 1), 123, 5.0),
				new MovieDto(2L, "AnotherTitle", LocalDate.of(1999, 1, 1), 123, 5.0)));

		mvc.perform(
				MockMvcRequestBuilders.get("/movies/search?title=MovieTitle")
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(movieDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	//	@Test
	//	void serchForMovieThatIsNotFound_ReturnsResponseStatusException404() throws Exception {
	//
	//		mvc.perform(
	//				MockMvcRequestBuilders.get("/movies/search?title=MovieTitle")
	//						.accept(MediaType.APPLICATION_JSON)
	//						.content(objectMapper.writeValueAsString(any(List.of())))
	//						.contentType(MediaType.APPLICATION_JSON))
	//				.andExpect(status().isOk());
	//
	//	}

	@Test
	void deleteDirectorWithInValidIdAndReturnStatusNotFound() throws Exception {
		doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(service).delete(1L);

		mvc.perform(MockMvcRequestBuilders.delete("/movies/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteMovieWithValidId_ReturnStatusOK() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/movies/{id}", 1))
				.andExpect(status().isOk());
	}


	//	@Test
	//	void whenNullValue_thenReturns400() throws Exception {
	//		MovieDto newMovieDto = new MovieDto(null, "Title", LocalDate.of(1999, 1, 1), 123, 5.0);
	//
	//		mvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
	//				.content(objectMapper.writeValueAsBytes(newMovieDto)))
	//				.andExpect(status().isBadRequest());
	//	}


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
