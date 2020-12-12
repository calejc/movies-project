package cale.spring.Movies.controller;

import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.MovieRepository;
import cale.spring.Movies.service.AuthorizationService;
import cale.spring.Movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Tuple;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class UpdateController {

    @Autowired
    MovieService movieService;

    public final String filename = "src/main/resources/authorized-usernames.txt";
    Map<String, String> authorizationMap = AuthorizationService.readInAuthorizedUsers(filename);

    public UpdateController() throws IOException { }

    @GetMapping("/update")
    public String add(Model model, Principal princpal){
        return "update";
//        String userName = getUsername(princpal);
//        String authorizations = authorizationMap.get(userName); //create,update
//        if (authorizations.indexOf("update")>= 0) {
//            model.addAttribute("pageTitle", "Update database");
//            return "update";
//        } else {
//            model.addAttribute("errorMessage", "403");
//            return "error";
//        }
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestParam("title") String title, @RequestParam("overview") String overview, Model model, Principal princpal){
//        String userName = getUsername(princpal);
//        String authorizations = authorizationMap.get(userName); //create,update
//        if (authorizations.contains("create")) {
            model.addAttribute("pageTitle", "Update database");
            MovieDTO movie = new MovieDTO(title, overview, 5.0);
            Movie savedMovie = movieService.addMovietoDB(movie);
            model.addAttribute("movie", savedMovie);
            return "success";
//        } else {
//            model.addAttribute("errorMessage");
//            return "probably jordan's fault";
//        }
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

    private String getUsername(Principal princpal) {
        return "joe";
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
