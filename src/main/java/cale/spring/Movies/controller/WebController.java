package cale.spring.Movies.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model){
        String currDate = (new Date()).toString();
        model.addAttribute("currDate", currDate);
        model.addAttribute("pageTitle", "Home");
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("pageTitle", "Add Movies");
        return "add";
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
