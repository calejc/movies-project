package cale.spring.Movies.Repositories;

import cale.spring.Movies.Models.Movie;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface MovieRepository extends JpaRepository<Movie, Long> {

//    @Query("")
//    @Query
//    @DynamicInsert
//    void saveMovie(Movie movie);

}
