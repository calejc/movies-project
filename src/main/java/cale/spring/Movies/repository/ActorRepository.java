package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
