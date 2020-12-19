package cale.spring.Movies.service;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.dto.MovieDTO;
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

    @Transactional
    public Movie addMovieToDB(MovieDTO movieDTO){
        Movie movie = new Movie(movieDTO.getId(), movieDTO.getTitle(), movieDTO.getOverview());
        Movie savedMovie = movieRepository.save(movie);
        if (movie.getId() != savedMovie.getId()) {
            System.out.println("Problem with adding movie!");
        }
        return savedMovie;
    }

    @Transactional
    public Actor addActorToDB(ActorDTO actorDTO) {
        Actor actor = new Actor(actorDTO.getId(), actorDTO.getName(), actorDTO.getBiography());
        Actor savedActor = actorRepository.save(actor);
        if (actor.getId() != savedActor.getId()) {
            System.out.println("Problem with adding actor!");
        }
        return savedActor;
    }

    @Transactional
    public void addMovieToActor(MovieDTO movieDTO, ActorDTO actorDTO){
        Movie movie = new Movie();
        Actor actor = new Actor();
        if (actorRepository.findById(actorDTO.getId()).isPresent() && movieRepository.findById(movieDTO.getId()).isPresent() && !movieDTO.getActors().contains(actorDTO)){

            actor = actorRepository.findById(actorDTO.getId()).get();
            movie = movieRepository.findById(movieDTO.getId()).get();

            actor.addMovie(movie);
            actorRepository.save(actor);
        }
    }

    @Transactional
    public void updateActor(ActorDTO actorDTO){
        if (actorRepository.findById(actorDTO.getId()).isPresent()){
            Actor actor = actorRepository.findById(actorDTO.getId()).get();
            actor.setName(actorDTO.getName());
            actor.setBiography(actorDTO.getBiography());
            actorRepository.save(actor);
        }
    }
    public void updateMovie(Movie movie){}
    public void delete(Object... args){}

    public Long generateNewMovieId(){
        boolean validId = false;
        Long rand;
        do {
            rand = Math.round(1 + Math.random()*50000);
            if (!movieRepository.existsById(rand)){
                validId = true;
            }
        } while(!validId);
        return rand;
    }

    public Long generateNewActorId(){
        boolean validId = false;
        Long rand;
        do {
            rand = Math.round(1 + Math.random()*100000);
            if (!actorRepository.existsById(rand)){
                validId = true;
            }
        } while(!validId);
        return rand;
    }




//    public Actor getActorById(Long id){}
//    public List<Actor> getAllActors(){}
//    public List<Actor> getActorsByName(){}
//    public Movie getMovieById(Long id){}
//    public List<Movie> getAllMovies(){}

}
