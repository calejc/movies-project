package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@DynamicUpdate
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByTitleContainingIgnoreCaseOrderByPopularity(String title);
    Page<Movie> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update movie m set m.title = :title where m.id = :id", nativeQuery = true)
    void updateMovieTitleById(@Param("title") String title, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update movie m set m.overview = :overview where m.id = :id", nativeQuery = true)
    void updateMovieOverviewById(@Param("overview") String overview, @Param("id") Long id);
}
