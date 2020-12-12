package cale.spring.Movies.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MovieDTO {

    @JsonSetter("movieId")
    private Long movieId;
    private String title;
    // DTO classes should not reference entity (aka model here) classes.
    private Set<ActorDTO> actors = new HashSet<>();

    public MovieDTO() {}
    public MovieDTO(Long movieId, String title, Set<ActorDTO> actors) {
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

    public Set<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(Set<ActorDTO> actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return this.movieId.equals(movieDTO.movieId) && this.title.equals(movieDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title);
    }
}
