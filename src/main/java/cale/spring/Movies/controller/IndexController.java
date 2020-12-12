package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class IndexController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;


    @GetMapping("/")
    public String index(Model model){
        String currDate = (new Date()).toString();
        model.addAttribute("currDate", currDate);
        model.addAttribute("pageTitle", "Movie Home");
        return "index";
    }

}
