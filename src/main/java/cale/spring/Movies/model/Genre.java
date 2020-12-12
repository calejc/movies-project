package cale.spring.Movies.model;

import cale.spring.Movies.model.Movie;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

//@Entity
public class Genre {

    @Id
    int id;
    String name;

//    @ManyToMany(mappedBy = "genres")
//    private Set<Movie> movies = new HashSet<>();

}
