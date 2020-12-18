package cale.spring.Movies.repository;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    public List<Genre> findAll();
    List<Genre> findByNameContainingIgnoreCase(String name);


}
