package cale.spring.Movies.DTO;

import cale.spring.Movies.Models.Actor;

import java.util.HashSet;
import java.util.Set;

public class MovieDTO {

    private Long movieId;
    private String title;
    private Set<Actor> actors = new HashSet<>();

    public MovieDTO() {}
    public MovieDTO(Long movieId, String title, Set<Actor> actors) {
        this.movieId = movieId;
        this.title = title;
        this.actors = actors;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
}
