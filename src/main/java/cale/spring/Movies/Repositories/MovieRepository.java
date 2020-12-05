package cale.spring.Movies.Repositories;

import cale.spring.Movies.Models.Movie;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface MovieRepository extends CrudRepository<Movie, Long> {

}
