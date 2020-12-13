package cale.spring.Movies.controller;

import cale.spring.Movies.authorization.Authorized;
import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.service.AuthorizationService;
import cale.spring.Movies.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class UpdateController {

    @Autowired
    CrudService crudService;
    public final String filename = "src/main/resources/authorized-usernames.txt";
    Map<String, String> authorizationMap = AuthorizationService.readInAuthorizedUsers(filename);

    public UpdateController() throws IOException { }

    @GetMapping("/update")
    public String returnContributePage(Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "create");
        if (authorized.getAuthorized()){
            return "update";
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestParam("title") String title, @RequestParam("overview") String overview,  Model model, Principal principal){
        String userName = "test";
        Authorized authorized = authorized(userName, "create");
        if (authorized.getAuthorized()) {
            Long id = crudService.generateNewMovieId();
            MovieDTO movie = new MovieDTO(id, title, overview, 5.9);
            Movie savedMovie = crudService.addMovieToDB(movie);
            System.out.println(savedMovie);
            model.addAttribute("successMessage", savedMovie.getTitle());
            return "redirect:/success";
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }


    @GetMapping("/delete")
    public String delete(Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "delete");
        if (authorized.getAuthorized()){
            model.addAttribute("pageTitle", "Update database");
            return "update";
        } else {
            return "error";
        }
    }

    @GetMapping("/success")
    public String successfulAction(Model model){
        return "success";
    }

    private Authorized authorized(String userName, String action) {
        Authorized authorized = new Authorized();
        if (authorizationMap.containsKey(userName)){
            Boolean auth = authorizationMap.get(userName).contains(action);
            authorized.setAuthorized(auth);
            if(!auth){
                authorized.setReturnMessage(String.format("Not authorized to perform %s actions", action));
            }
        } else {
            authorized.setAuthorized(false);
            authorized.setReturnMessage("Not authorized to perform any actions");
        }
        return authorized;
    }




}
