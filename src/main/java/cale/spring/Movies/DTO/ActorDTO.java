package cale.spring.Movies.DTO;

import cale.spring.Movies.Models.Movie;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ActorDTO {

    private Long actorId;
    private String name;
    private Set<Movie> movies = new HashSet<>();

    public ActorDTO() {}
    public ActorDTO(Long actorId, String name, Set<Movie> movies) {
        this.actorId = actorId;
        this.name = name;
        this.movies = movies;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return this.actorId.equals(actorDTO.actorId) && this.name.equals(actorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
