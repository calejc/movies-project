//package cale.spring.Movies.Models;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Genre {
//
//    @Id
//    int genreId;
//    String type;
//    List<Movie> movies;
//
//    public List<Movie> getMovies() {
//        return movies;
//    }
//
//    public void setMovies(List<Movie> movies) {
//        this.movies = movies;
//    }
//
//    public Genre() {
//        this(-1, null);
//        movies = new ArrayList<>();
//    }
//
//    public Genre(int genreId, String type) {
//        super();
//        this.genreId = genreId;
//        this.type = type;
//    }
//
//    public String toString() {
//        return String.format("Genre(%d,%s)", genreId, type);
//    }
//
//    public int getGenreId() {
//        return genreId;
//    }
//
//    public void setGenreId(int genreId) {
//        this.genreId = genreId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//}
