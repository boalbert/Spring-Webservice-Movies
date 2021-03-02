package se.boalbert.moviesapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import se.boalbert.moviesapi.dto.MovieDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoviesapiApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate testClient;

	@Test
	void contextLoads() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/xml");
		//testClient.exchange("localhost:8080/person", HttpMethod.GET, new HttpEntity<>(headers), PersonDto[].class);

		var result = testClient.getForEntity("http://localhost:"+port+"/movies", MovieDto[].class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().length).isGreaterThan(0);
	}

}
