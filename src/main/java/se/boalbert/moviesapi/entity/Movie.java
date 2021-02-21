package se.boalbert.moviesapi.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	private String title;
	private LocalDate releaseDate;
	private int runTimeMins;
	private double imdbRating;
	//private List<Directors> - köra en list så man kan hantera flera regissörer?


	public Movie() {
	}

	public Movie(long id, String title, LocalDate releaseDate, int runTimeMins, double imdbRating) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.runTimeMins = runTimeMins;
		this.imdbRating = imdbRating;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id=" + id +
				", title='" + title + '\'' +
				", releaseDate=" + releaseDate +
				", runTimeMins=" + runTimeMins +
				", imdbRating=" + imdbRating +
				'}';
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
