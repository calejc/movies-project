package cale.spring.Movies.controller;

import cale.spring.Movies.dto.MovieDTO;
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
import java.util.Map;

@Controller
public class UpdateController {

    @Autowired
    CrudService crudService;
    public final String filename = "src/main/resources/authorized-usernames.txt";
    Map<String, String> authorizationMap = AuthorizationService.readInAuthorizedUsers(filename);

    public UpdateController() throws IOException { }

    @GetMapping("/update")
    public String add(Model model, Principal principal){
        return "update";
//        String userName = getUsername(principal);
//        String authorizations = authorizationMap.get(userName); //create,update
//        if (authorizations.contains("update")) {
//            model.addAttribute("pageTitle", "Update database");
//            return "update";
//        } else {
//            model.addAttribute("errorMessage", "403");
//            return "error";
//        }
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestParam("title") String title, @RequestParam("overview") String overview,  Model model, Principal principal){
        String userName = getUsername(principal);
        String authorizations = authorizationMap.get(userName); //create,update
        if (authorizations.contains("create")) {
            Long id = crudService.generateNewMovieId();
            MovieDTO movie = new MovieDTO(id, title, overview, 5.9);
            Movie savedMovie = crudService.addMovieToDB(movie);
            System.out.println(savedMovie);
            model.addAttribute("successMessage", savedMovie.getTitle());
            return "success";
        } else {
            model.addAttribute("errorMessage");
            return "error";
        }
    }


    @GetMapping("/delete")
    public String delete(Model model, Principal princpal){
        String userName = getUsername(princpal);
        String authorizations = authorizationMap.get(userName); //create,update
        if (authorizations.indexOf("delete")>= 0) {
            model.addAttribute("pageTitle", "Update database");
            return "update";
        } else {
            return "GO AWAY";
        }
    }

    private String getUsername(Principal principal) {
        String userName;
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        userName = (String) attributes.get("login");
        return userName;
//        try {
//            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
//            Map<String, Object> attributes = token.getPrincipal().getAttributes();
//            userName = (String) attributes.get("login");
//            return userName;
//        } catch (Exception e) {
//            e.printStackTrace(); //TODO: use a logger!
//        }
    }

    @PostMapping("/success")
    public String update(@RequestBody Model model) {

        model.getAttribute("actorName");
        model.getAttribute("movieName");
        model.getAttribute("movieGenre");
        model.getAttribute("movieRating");
        model.getAttribute("moviePhotoUrl");
        return "success";
    }

}
