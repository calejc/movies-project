package cale.spring.Movies.service;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CrudService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;

    public void addActor(Actor actor){ actorRepository.save(actor); }
    public void addMovie(Movie movie){ movieRepository.save(movie); }
    public void updateActor(Actor actor) throws Exception {
        Optional<Actor> actorEntry = actorRepository.findById(actor.getId());
        if (actorEntry.isPresent()){
            Actor updatedActor = actorEntry.get();
            updatedActor.setName(actor.getName());
            updatedActor.setGender(actor.getGender());
            updatedActor.setBiography(actor.getBiography());
            updatedActor.setBirthday(actor.getBirthday());
            updatedActor.setDeathday(actor.getDeathday());
            updatedActor.setPopularity(actor.getPopularity());
            updatedActor.setMovies(actor.getMovies());
        } else {
             throw new Exception("NOT FOUND");
        }
    }
    public void updateMovie(Movie movie){}
    public void delete(Object... args){}

    public Long generateNewMovieId() {
        boolean validId = false;
        Long rand;
        do {
            rand = Math.round(1 + Math.random() * 100000);
            if (movieRepository.existsById(rand)) {
                validId = true;
            }
        } while (validId == false);
        return rand;
    }

    public Long generateNewActorId() {
        boolean validId = false;
        Long rand;
        do {
            rand = Math.round(1 + Math.random() * 100000);
            if (actorRepository.existsById(rand)) {
                validId = true;
            }
        } while (validId == false);
        return rand;
    }

//    public Actor getActorById(Long id){}
//    public List<Actor> getAllActors(){}
//    public List<Actor> getActorsByName(){}
//    public Movie getMovieById(Long id){}
//    public List<Movie> getAllMovies(){}

}
