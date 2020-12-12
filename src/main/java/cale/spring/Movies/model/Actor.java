package cale.spring.Movies.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Actor {

    @Id
    private Long id;
    private String name, photoUrl;
    @Lob
    private String biography;
    private Date birthday, deathday;
    private Integer gender;
    private Double popularity;


    public Actor(){ }

    public Actor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Actor(Long id, String name, String photoUrl, String biography, Date birthday, Date deathday, Integer gender, Double popularity) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.popularity = popularity;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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
                ", photoUrl='" + photoUrl + '\'' +
                ", birthday=" + birthday +
                ", deathday=" + deathday +
                ", gender=" + gender +
                ", popularity=" + popularity +
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
}
