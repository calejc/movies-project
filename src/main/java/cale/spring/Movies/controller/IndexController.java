package cale.spring.Movies.controller;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;


    @GetMapping("/")
    public String index(Model model){
        String currDate = (new Date()).toString();
        List<Actor> actors = actorRepository.findAll();
        List<Movie> movies = movieRepository.findAll();
        Actor randomActor = new Actor();
        do{
            randomActor = actors.get(returnRand(actors.size()));
        } while (randomActor.getPhotoUrl().contains("no_img"));
        Movie randomMovie = movies.get(returnRand(movies.size()));
        model.addAttribute("randomActor", randomActor);
        model.addAttribute("randomMovie", randomMovie);
        model.addAttribute("currDate", currDate);
        model.addAttribute("pageTitle", "Movie Home");
        return "index";
    }

    public Integer returnRand(int listSize){
        Random rand = new Random();
        return rand.nextInt(listSize);
    }

}
