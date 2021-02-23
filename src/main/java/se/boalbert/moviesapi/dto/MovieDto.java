package se.boalbert.moviesapi.dto;

import java.time.LocalDate;

public class MovieDto {
	private long id;
	private String title;
	private LocalDate releaseDate;
	private int runTimeMins;
	private double imdbRating;

	public MovieDto(long id, String title, LocalDate releaseDate, int runTimeMins, double imdbRating) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.runTimeMins = runTimeMins;
		this.imdbRating = imdbRating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getRunTimeMins() {
		return runTimeMins;
	}

	public void setRunTimeMins(int runTimeMins) {
		this.runTimeMins = runTimeMins;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}
}
