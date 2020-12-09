package cale.spring.Movies.service;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addMovie(Movie[] movies){
//        movieRepository.save(movie);
        for (Movie movie : movies){
            Set<Actor> actors = movie.getActors();
            for (Actor actor : actors){
                actor.addMovie(movie);
            }
        }
        for (Movie movie : movies){
            for (Actor actor : movie.getActors()){
                actorRepository.save(actor);
            }
        }
    }

}
