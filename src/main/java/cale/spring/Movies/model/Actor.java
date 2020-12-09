package cale.spring.Movies.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Actor {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Actor(){ }

    public Actor(Long id, String name, Set<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "actor_movie",
            joinColumns = {@JoinColumn(name = "actor_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    private Set<Movie> movies = new HashSet<>();

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", movies=" + movies.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor1 = (Actor) o;
        return Objects.equals(this.id, actor1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
