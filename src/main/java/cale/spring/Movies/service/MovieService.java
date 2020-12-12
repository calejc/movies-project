package cale.spring.Movies.service;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
@DynamicInsert
public class MovieService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private Integer counter = 0;

    public MovieService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public void addToCounter(){
        Integer c = getCounter();
        c++;
        setCounter(c);
    }

    // Changed to follow example from notes app (Url is like a Movie)
    @Transactional
    public void addMovie(MovieDTO movieDTO){
        Movie movie = new Movie(movieDTO.getId(),movieDTO.getTitle(), movieDTO.getPhotoUrl(), movieDTO.getOverview(), movieDTO.getReleaseDate(), movieDTO.getVoteAverage(), movieDTO.getPopularity());
        Movie savedMovie = movieRepository.save(movie);
        if (movie.getId() != savedMovie.getId()) {
            System.out.println("Problem!");
        }
        for (ActorDTO actorDTO: movieDTO.getActors()) {
            Optional<Actor> optional = actorRepository.findById(actorDTO.getId());
            Actor actor = optional.orElseGet(() -> new Actor(actorDTO.getId(), actorDTO.getName(), actorDTO.getPhotoUrl(), actorDTO.getBiography(), actorDTO.getBirthday(), actorDTO.getDeathday(), actorDTO.getGender(), actorDTO.getPopularity()));
            actor.addMovie(movie);
            Actor savedActor = actorRepository.save(actor);
            if (actor.getId() != savedActor.getId()) {
                System.out.println("Problem saving actor!");
            }
        }
    }
    @Transactional
    public Movie addMovietoDB(MovieDTO movieDTO){
        Movie movie = new Movie(movieDTO.getId(), movieDTO.getTitle(), movieDTO.getOverview(), movieDTO.getPopularity());
        Movie savedMovie = movieRepository.save(movie);
        if (movie.getId() != savedMovie.getId()) {
            System.out.println("Problem!");
        }
        return savedMovie;
    }

}
