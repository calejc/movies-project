package cale.spring.Movies.controller;

import cale.spring.Movies.model.Genre;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.GenreRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Controller
@Transactional
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/genres")
    public String genre(Model model) {
        List<Genre> genres = genreRepository.findAll();
        List<Movie> movies = movieRepository.findAll();
        Movie randomMovie = new Movie();
        do{
            randomMovie = movies.get(returnRand(movies.size()));
        } while (randomMovie.getPhotoUrl().contains("default"));
        model.addAttribute("randomMovie", randomMovie);
        model.addAttribute("genres", randomImgs(genreRepository.findAll()));
        return "genres";
    }

    @GetMapping("/genre/{id}")
    public ModelAndView genreView(@PathVariable("id") Long id, ModelAndView mav) {

        if (genreRepository.findById(id).isEmpty()) {
            mav.setViewName("error");
            String errorMessage = String.format("Genre %d not found", id);
            mav.addObject("errorMessage", errorMessage);
        } else {
            mav.setViewName("genre");
            String genreName = genreRepository.findById(id).get().getName();
            List<Movie> movies = movieRepository.findAllMoviesByGenreType(id);
            String photoUrl;
            int randIndex = returnRand(movies.size());
            long randomMovieId = movies.get(randIndex).getId();
            if (movieRepository.findById(randomMovieId).isPresent()) {
                Movie movie = movieRepository.findById(randomMovieId).get();
                photoUrl = movie.getPhotoUrl();
            } else {
                photoUrl = "/imgs/OIP.jpg";
            }
            mav.addObject("photoUrl" , photoUrl);
            mav.addObject("genre", genreRepository.findById(id).get());
            mav.addObject("pageTitle",genreName);
            mav.addObject("movies", genreRepository.findById(id).get().getMovies());
        }
        return mav;
    }


    public Integer returnRand(int listSize){
        Random rand = new Random();
        return rand.nextInt(listSize);
    }

    public Map<Genre, String> randomImgs(List<Genre> genres) {
        Map<Genre, String> genreMap = new HashMap<>();
        for (Genre genre : genres) {
            List<Movie> movies = movieRepository.findAllMoviesByGenreType(genre.getId());
            String photoUrl;
            int randIndex = returnRand(movies.size());
            long randomMovieId = movies.get(randIndex).getId();
            if (movieRepository.findById(randomMovieId).isPresent()) {
                Movie movie = movieRepository.findById(randomMovieId).get();
                photoUrl = movie.getPhotoUrl();
            } else {
                photoUrl = "/img/defaultGenre.jpg";
            }
            genreMap.put(genre, photoUrl);

        }
        return genreMap;
    }



}
