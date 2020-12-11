package cale.spring.Movies.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Movie {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, photoUrl;
    @Lob
    private String overview;
    @JsonSetter("release_date")
    private Date releaseDate;
    @JsonSetter("vote_average")
    private Double voteAverage;
    private Double popularity;
//    @JsonSetter("genre_ids")
//    private List<Integer> genreIds;

    public Movie(Long id, String title, String photoUrl, String overview, Date releaseDate, Double voteAverage, Double popularity, Set<Actor> actors) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.actors = actors;
    }

    public Movie(){ }
    public Movie(Long id, String title, Set<Actor> actors, String photoUrl) {
        this.id = id;
        this.title = title;
        this.actors = actors;
        this.photoUrl = photoUrl;
    }

//    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.getMovies().remove(this);
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

//    public List<Integer> getGenreIds() {
//        return genreIds;
//    }
//
//    public void setGenreIds(List<Integer> genreIds) {
//        this.genreIds = genreIds;
//    }
}
