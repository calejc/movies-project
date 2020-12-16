package cale.spring.Movies.dto;

import cale.spring.Movies.model.Movie;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Lob;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ActorDTO {

    @JsonSetter("id")
    private Long id;
    private String name, photoUrl;
    @Lob
    private String biography;
    private Date birthday, deathday;
    private Integer gender;
    private Double popularity;
    private Set<Movie> movies = new HashSet<>();

    public ActorDTO() {}

    public ActorDTO(Long id, String name, String photoUrl, String biography, Date birthday, Date deathday, Integer gender, Double popularity, Set<Movie> movies) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.popularity = popularity;
        this.movies = movies;
    }

    public ActorDTO(Long id, String name, String photoUrl, String biography, Date birthday, Date deathday, Integer gender, Double popularity) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.popularity = popularity;
    }

    public ActorDTO(Long id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDeathday() {
        return deathday;
    }

    public void setDeathday(Date deathday) {
        this.deathday = deathday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
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
        if (id == null || actorDTO.id == null || this.name == null) {
            System.out.println("HEY!");
        }
        return this.id.equals(actorDTO.id) && this.name.equals(actorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
