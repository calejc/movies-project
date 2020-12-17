package cale.spring.Movies.service;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.dto.GenreDTO;
import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Genre;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@DynamicInsert
public class MovieService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;


    public MovieService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }


    // Changed to follow example from notes app (Url is like a Movie)
    @Transactional
    public void addMovie(MovieDTO movieDTO){
        Movie movie = new Movie(movieDTO.getId(),movieDTO.getTitle(), movieDTO.getPhotoUrl(), movieDTO.getOverview(), movieDTO.getReleaseDate(), movieDTO.getVoteAverage(), movieDTO.getPopularity());
        for (GenreDTO genreDTO : movieDTO.getGenres()){
            Genre genre = new Genre(genreDTO.getId(), genreDTO.getName());
            movie.addGenre(genre);
        }
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

}
