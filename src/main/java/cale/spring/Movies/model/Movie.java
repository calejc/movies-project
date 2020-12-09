package cale.spring.Movies.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSetter("movieId")
    private Long id;
    String title;

    public Movie(){ }
    public Movie(Long id, String title, Set<Actor> actors) {
        this.id = id;
        this.title = title;
        this.actors = actors;
    }

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(mappedBy = "movies", fetch=FetchType.LAZY)
    private Set<Actor> actors = new HashSet<>();

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", actors=" + actors.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return this.id.equals(movie.id);
    }

//    public void addActor(Actor actor) {
//        this.actors.add(actor);
//        actor.getMovies().add(this);
//    }
//
//    public void removeActor(Actor actor) {
//        this.actors.remove(actor);
//        actor.getMovies().remove(this);
//    }

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
