package cale.spring.Movies.Services;

import cale.spring.Movies.Models.Actor;
import cale.spring.Movies.Models.Movie;
import cale.spring.Movies.Repositories.ActorRepository;
import cale.spring.Movies.Repositories.MovieRepository;
import cale.spring.Movies.Repositories.MovieRepository;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    public void addMovie(Movie movie){
//        movieRepository.save(movie);
        Set<Actor> actors = movie.getActors();
        for (Actor actor : actors){
            actor.addMovie(movie);
            movieRepository.save(movie);
            actorRepository.save(actor);
        }
    }

}
