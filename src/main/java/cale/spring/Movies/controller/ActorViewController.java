package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActorViewController {

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/actors/{id}")
    public String actorView(@RequestParam Long id, Model model){
        String slug;
        if (actorRepository.findById(id).isPresent()){
            String actorName = actorRepository.findById(id).get().getName();
            slug = actorName.toLowerCase().replace(" ", "-");
        } else {
            return "error";
        }
        model.addAttribute("pageTitle", slug);
        actorRepository.findById(id);
        return "actor-view";
    }
}
