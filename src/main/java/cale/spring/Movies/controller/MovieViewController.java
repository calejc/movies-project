package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieViewController {
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/movie/{id}")
    public String actorView(@RequestParam Long id, Model model){
        String slug;
        if (movieRepository.findById(id).isPresent()){
            String actorName = movieRepository.findById(id).get().getTitle();
            slug = actorName.toLowerCase().replace(" ", "-");
        } else {
            return "error";
        }
        model.addAttribute("pageTitle", slug);
        movieRepository.findById(id);
        return "movie";
    }
}
