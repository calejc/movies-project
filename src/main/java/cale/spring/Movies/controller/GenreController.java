package cale.spring.Movies.controller;

import cale.spring.Movies.model.Genre;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.GenreRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
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
        model.addAttribute("genres", genres);
        return "genres";
    }

    public Integer returnRand(int listSize){
        Random rand = new Random();
        return rand.nextInt(listSize);
    }



}
