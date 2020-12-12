package cale.spring.Movies.dto;

import cale.spring.Movies.model.Movie;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ActorDTO {

    @JsonSetter("id")
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
        if (actorId == null || actorDTO.actorId == null || this.name == null) {
            System.out.println("HEY!");
        }
        return this.actorId.equals(actorDTO.actorId) && this.name.equals(actorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
