package cale.spring.Movies.Models;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonSetter("movieId")
    private Long id;
    String title;

    public Movie(){}
    public Movie(Long id, String title, Set<Actor> actors) {
        this.id = id;
        this.title = title;
        this.actors = actors;
    }

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors = new HashSet<>();

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", actors=" + actors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return this.id.equals(movie.id) && title.equals(movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
