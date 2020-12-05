package cale.spring.Movies.Repositories;

import cale.spring.Movies.Models.Actor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ActorRepository extends CrudRepository<Actor, Long> {
}
