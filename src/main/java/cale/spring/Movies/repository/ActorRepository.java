package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@DynamicUpdate
@Transactional
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByNameContainingIgnoreCaseOrderByPopularity(String name);
    List<Actor> findByNameContainingIgnoreCase(String name);
    List<Actor> findAllByOrderByName();

    @Query(value = "select a.id, a.biography, a.birthday, a.deathday, a.gender, a.name, a.photo_url, a.popularity from actor a inner join actor_movie am on a.id = am.actor_id inner join movie_genre mg on am.movie_id = mg.movie_id where genre_id = :genreId", nativeQuery = true)
    List<Actor> findActorByGenreType(Long genreId);

    @Modifying
    @Query(value = "update actor a set a.name = :name where a.id = :id", nativeQuery = true)
    void updateActorNameById(@Param("name") String name, @Param("id") Long id);

    @Modifying
    @Query(value = "update actor a set a.biography = :biography where a.id = :id", nativeQuery = true)
    void updateActorBiographyById(@Param("id") Long id, @Param("biography") String biography);

    @Query(value = "select a.id, a.biography, a.birthday, a.deathday, a.gender, a.name, a.photo_url, a.popularity from actor a inner join actor_movie am on a.id = am.actor_id inner join movie_genre mg on am.movie_id = mg.movie_id where genre_id = :genreId and a.name like %:name%", nativeQuery = true)
    List<Actor> findByNameContainingIgnoreCaseFilterByGenreType(@Param("genreId") Long genreId, @Param("name") String name);

}
