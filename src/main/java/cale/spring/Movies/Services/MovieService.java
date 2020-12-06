package cale.spring.Movies.Services;

import cale.spring.Movies.Models.Actor;
import cale.spring.Movies.Models.Movie;
import cale.spring.Movies.Repositories.ActorRepository;
import cale.spring.Movies.Repositories.MovieRepository;
import cale.spring.Movies.Repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
public class MovieService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public MovieService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void addMovie(Movie movie){
        Set<Actor> actors = movie.getActors();
        for (Actor actor : actors){
//            actor.setMovies(movie);
//            System.out.println(actor.getMovies());
//            System.out.println(actor);
            actorRepository.save(actor);
//            actor.addMovie(movie);
        }
        movieRepository.save(movie);
    }

}
