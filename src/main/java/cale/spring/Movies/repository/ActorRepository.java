package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByNameContainingIgnoreCaseOrderByPopularity(String name);
    List<Actor> findByNameContainingIgnoreCase(String name);
    List<Actor> findAllByOrderByName();
//    Page<Actor> findAll(Pageable pageable);
//    List<Actor> findAll();

//    @Query(value = "select * from movie inner join movie_genre on movie.id = movie_genre.movie_id where genre_id = :genreId", nativeQuery = true)
//    List<Movie> findActorByGenreType(Long genreId);
    @Query(value = "select * from movie inner join movie_genre on movie.id = movie_genre.movie_id where movie_genre.genre_id = :genreId", nativeQuery = true)
    List<Movie> findAllActorsByGenreType(@Param("genreId") Long genreId);

    @Query(value = "select * from movie inner join movie_genre on movie.id = movie_genre.movie_id where movie_genre.genre_id = :genreId and movie.title like %:title%", nativeQuery = true)
    List<Movie> findByTitleContainingIgnoreCaseFilterByGenreType(@Param("genreId") Long genreId, @Param("title") String title);

    @Query(value = "select * from movie inner join movie_genre on movie.id = movie_genre.movie_id where movie_genre.genre_id = :genreId and movie.overview like %:overview%", nativeQuery = true)
    List<Movie> findByOverviewContainingIgnoreCaseFilterByGenreType(@Param("genreId") Long genreId, @Param("overview") String overview);
}
