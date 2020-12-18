package cale.spring.Movies.controller;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.model.Result;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/search")
    public String renderSearchResults(@RequestParam Map<String, String> allParams, Model model){
        List<Result> results = new ArrayList<>();
        if (allParams.containsKey("actor")) {
            //do actorRepo search
            List<Actor> actorsFound = actorRepository.findByNameContainingIgnoreCase(allParams.get("q"));
            results.addAll(convertListOfActorsToResultType(actorsFound));

        } else if (allParams.containsKey("movie")) {
            //do movieRepo search
            List<Movie> moviesFound = movieRepository.findByOverviewIgnoreCase(allParams.get("q"));
            List<Movie> titlesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(allParams.get("q"));
            moviesFound.addAll(titlesFound);
            results.addAll(convertListOfMoviesToResultType(moviesFound));

        } else {
            //search both movies and actors
            List<Actor> actorsFound = actorRepository.findByNameContainingIgnoreCase(allParams.get("q"));
            List<Movie> moviesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(allParams.get("q"));
            results.addAll(convertListOfActorsToResultType(actorsFound));
            results.addAll(convertListOfMoviesToResultType(moviesFound));
        }

        model.addAttribute("results", results);
        model.addAttribute("pageTitle", "Search Results");
        return "search";
    }

    public List<Result> convertListOfActorsToResultType(List<Actor> actorsFound) {
        List<Result> results = new ArrayList<>();
        for (Actor actor : actorsFound) {
            Result result = new Result(actor.getId(), actor.getName(), actor.getPhotoUrl(), "actor");
            results.add(result);
        }
        return results;
    }

    public List<Result> convertListOfMoviesToResultType(List<Movie> moviesFound) {
        List<Result> results = new ArrayList<>();
        for (Movie movie : moviesFound) {
            Result result = new Result(movie.getId(), movie.getTitle(), movie.getPhotoUrl(), "movie");
            results.add(result);
        }
        return results;
    }

}
