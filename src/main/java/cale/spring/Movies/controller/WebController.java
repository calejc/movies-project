package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;


    @GetMapping("/")
    public String index(Model model){
        String currDate = (new Date()).toString();
        model.addAttribute("currDate", currDate);
        model.addAttribute("pageTitle", "Home");
        return "index";
    }

    @GetMapping("/update")
    public String add(Model model){
        model.addAttribute("pageTitle", "Update database");
        return "update";
    }

    @GetMapping("/movies")
    public String movies(Model model){
        model.addAttribute("pageTitle", "Movies");
        return "movies";
    }

    @GetMapping("/actors")
    public String actors(Model model){
        model.addAttribute("pageTitle", "Actors");
        return "actors";
    }
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


    @GetMapping("/account")
    public String account(Model model, Principal principal){
        try {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            Map<String, Object> attributes = token.getPrincipal().getAttributes();
            System.out.println(attributes);
            String login = (String) attributes.get("login");
            String name = (String) attributes.get("name");
            String email = (String) attributes.get("email");
            model.addAttribute("pageTitle", "Account");
            model.addAttribute("login", login);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: use a logger!
        }
        return "account";
    }

}
