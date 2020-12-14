package cale.spring.Movies.controller;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/search")
    public String renderSearchResults(@RequestParam("q") String q, Model model){
        List<Actor> actorsFound = actorRepository.findByNameContainingIgnoreCase(q);
        List<Movie> moviesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(q);
        model.addAttribute("actorsFound", actorsFound);
        model.addAttribute("moviesFound", moviesFound);
        model.addAttribute("pageTitle", "Search Results");
        return "search";
    }

}
