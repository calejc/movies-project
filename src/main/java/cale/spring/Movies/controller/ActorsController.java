package cale.spring.Movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActorsController {

    @GetMapping("/actors")
    public String actors(Model model){
        model.addAttribute("pageTitle", "Actors");
        return "actors";
    }
}
