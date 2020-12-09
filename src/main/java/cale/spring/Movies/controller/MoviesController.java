package cale.spring.Movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoviesController {

    @GetMapping("/movies")
    public String movies(Model model){
        model.addAttribute("pageTitle", "Movies");
        return "movies";
    }
}
