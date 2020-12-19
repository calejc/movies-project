package cale.spring.Movies.controller;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Genre;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.model.Result;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.GenreRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private GenreRepository genreRepository;


    @GetMapping("/search")
    public String renderSearchResults(@RequestParam Map<String, String> allParams, Model model) {
        List<Genre> genres = genreRepository.findAll();
        List<Genre> genreColumn1 = new ArrayList<>();
        List<Genre> genreColumn2 = new ArrayList<>();
        List<Genre> genreColumn3 = new ArrayList<>();
        List<Genre> genreColumn4 = new ArrayList<>();

        List<Result> results = new ArrayList<>();
        List<Long> genreIds = new ArrayList<>();

        List<String> nonGenreParams = List.of("id", "q", "actor", "movie");
        for (Map.Entry<String, String> entry : allParams.entrySet()){
            if (!nonGenreParams.contains(entry.getKey()) && genreRepository.findById(Long.parseLong(entry.getKey())).isPresent()){
                genreIds.add(Long.parseLong(entry.getKey()));
            }
        }


        if (allParams.containsKey("actor")) {
            //do actorRepo search
            if (genreIds.size() == 0){
                List<Actor> actorsFound = actorRepository.findByNameContainingIgnoreCase(allParams.get("q"));
                results.addAll(convertListOfActorsToResultType(actorsFound));
            } else {
                for (Long genreId : genreIds){
                    results.addAll(convertListOfActorsToResultType(actorRepository.findByNameContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                }
            }

        } else if (allParams.containsKey("movie")) {

            //do movieRepo search
            if (genreIds.size() == 0){
                List<Movie> moviesFound = movieRepository.findByOverviewIgnoreCase(allParams.get("q"));
                List<Movie> titlesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(allParams.get("q"));
                moviesFound.addAll(titlesFound);
                results.addAll(convertListOfMoviesToResultType(moviesFound));
            } else {
                for (Long genreId : genreIds){
                    results.addAll(convertListOfMoviesToResultType(movieRepository.findByTitleContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                    results.addAll(convertListOfMoviesToResultType(movieRepository.findByOverviewContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                }
            }
        } else {
            //search both movies and actors
            if (genreIds.size() == 0){
                List<Movie> titlesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(allParams.get("q"));
                List<Actor> actorsFound = actorRepository.findByNameContainingIgnoreCase(allParams.get("q"));
                List<Movie> moviesFound = movieRepository.findByTitleContainingIgnoreCaseOrderByPopularity(allParams.get("q"));
                moviesFound.addAll(titlesFound);
                results.addAll(convertListOfMoviesToResultType(moviesFound));
                results.addAll(convertListOfActorsToResultType(actorsFound));
            } else {
                for (Long genreId : genreIds){
                    //TODO
                    // do actor search by genre
                    results.addAll(convertListOfMoviesToResultType(movieRepository.findByTitleContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                    results.addAll(convertListOfMoviesToResultType(movieRepository.findByOverviewContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                    results.addAll(convertListOfActorsToResultType(actorRepository.findByNameContainingIgnoreCaseFilterByGenreType(genreId, allParams.get("q"))));
                }
            }
        }
        model.addAttribute("genres", genres);
        model.addAttribute("results", new ArrayList<>(new HashSet<>(results)));
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
