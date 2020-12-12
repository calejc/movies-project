package cale.spring.Movies.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.transaction.annotation.Transactional;
import cale.spring.Movies.model.Genre;
import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
public class Movie {

    @Id
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
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "movie_genre",
//            joinColumns = {@JoinColumn(name = "")},
//            inverseJoinColumns = {@JoinColumn(name = "")}
//    )
//    private Set<Genre> genres = new HashSet<>();

    public Movie(){ }
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
    public Movie(Long id, String title, String photoUrl, String overview, Date releaseDate, Double voteAverage, Double popularity) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }


    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors = new HashSet<>();

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", releaseDate=" + releaseDate +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
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
