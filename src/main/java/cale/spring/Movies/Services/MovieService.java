package cale.spring.Movies.Services;

import cale.spring.Movies.Models.Actor;
import cale.spring.Movies.Models.Movie;
import cale.spring.Movies.Repositories.ActorRepository;
import cale.spring.Movies.Repositories.MovieRepository;
import org.springframework.stereotype.Service;
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
            actor.setId(null);
            actorRepository.save(actor);
//            actor.setId(null);
//            actor.addMovie(movie);
        }
        movie.setId(null);
        movieRepository.save(movie);
//        movie.setId(null);
    }

}
