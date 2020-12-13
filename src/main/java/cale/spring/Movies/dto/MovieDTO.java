package cale.spring.Movies.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Lob;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MovieDTO {


    private Long id;
    private String title, photoUrl;
    @Lob
    private String overview;
    @JsonSetter("release_date")
    private Date releaseDate;
    @JsonSetter("vote_average")
    private Double voteAverage;
    private Double popularity;
    private Set<ActorDTO> actors = new HashSet<>();


    public MovieDTO() {}

    public MovieDTO(Long id, String title, String overview, Double popularity) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
    }

    public MovieDTO(Long id, String title, String photoUrl, String overview, Date releaseDate, Double voteAverage, Double popularity, Set<ActorDTO> actors) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.actors = actors;
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
        return this.id.equals(movieDTO.id) && this.title.equals(movieDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title);
    }
}
