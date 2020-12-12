package cale.spring.Movies.controller;

import cale.spring.Movies.repository.MovieRepository;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class MoviesController {

    @Autowired
    MovieRepository movieRepository;

//    @GetMapping("/movie")
//    public Model movie(@SafeHtml.Attribute())
    @GetMapping("/movies")
    public String movies(Model model){
        model.addAttribute("pageTitle", "Movies");
        return "movies";
    }
}

//href for actor is /movie/<id> and actor/id/
