package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findAll(Pageable pageable);
}
