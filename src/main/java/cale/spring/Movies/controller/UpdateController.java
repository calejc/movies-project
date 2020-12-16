package cale.spring.Movies.controller;

import cale.spring.Movies.authorization.Authorized;
import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import cale.spring.Movies.service.AuthorizationService;
import cale.spring.Movies.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
public class UpdateController {

    @Autowired
    CrudService crudService;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;
    public final String filename = "src/main/resources/authorized-usernames.txt";
    Map<String, String> authorizationMap = AuthorizationService.readInAuthorizedUsers(filename);

    public UpdateController() throws IOException { }

    @GetMapping("/update")
    public String returnContributePage(Model model, Principal principal){
        model.addAttribute("pageTitle", "Update");
        return "update";
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestParam("title") String title, @RequestParam("overview") String overview,  Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "create");
        if (authorized.getAuthorized()) {
            MovieDTO movie = new MovieDTO(crudService.generateNewMovieId(), title, overview);
            Movie savedMovie = crudService.addMovieToDB(movie);
            System.out.println(savedMovie.getTitle());
            model.addAttribute("successMessage", savedMovie.getTitle());
            return "success";
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @PostMapping("/addActor")
    public String addActor(@RequestParam("name") String name, @RequestParam("biography") String biography,  Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "create");
        if (authorized.getAuthorized()) {
            ActorDTO actor = new ActorDTO(crudService.generateNewActorId(), name, biography);
            Actor savedActor = crudService.addActorToDB(actor);
            System.out.println(savedActor.getName());
            model.addAttribute("successMessage", savedActor.getName());
            return "success";
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }


    @GetMapping("/edit-actor")
    public String editActorForm(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "update");
        if (authorized.getAuthorized()){
            if (actorRepository.findById(id).isPresent()){
                Actor actor = actorRepository.findById(id).get();
                model.addAttribute("actor", actor);
                return "edit-actor";
            } else {
                model.addAttribute("errorMessage", String.format("Actor %d not found", id));
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @GetMapping("/edit-movie")
    public String editMovieForm(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "update");
        if (authorized.getAuthorized()){
            if (movieRepository.findById(id).isPresent()){
                Movie movie = movieRepository.findById(id).get();
                model.addAttribute("movie", movie);
                return "edit-movie";
            } else {
                model.addAttribute("errorMessage", String.format("Movie %d not found", id));
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @PostMapping("/edit-movie")
    public String editMovie(@RequestParam Long id, @RequestParam String title, Model model){
        System.out.println(movieRepository.findById(id));
        System.out.printf("ID: %d    ||     Title: %s", id, title);
        movieRepository.updateMovieTitleById(title, id);
        model.addAttribute("successMessage", String.format("Title for movie #%d updated to %s", id, title));
        return "success";
    }


    @GetMapping("/delete-actor")
    public String deleteActor(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "delete");
        if (authorized.getAuthorized()){
            if (actorRepository.existsById(id)){
                String actorName = actorRepository.findById(id).get().getName();
                actorRepository.delete(actorRepository.findById(id).get());
                if (!actorRepository.existsById(id)){
                    model.addAttribute("successMessage", String.format("Successfully deleted actor %s", actorName));
                    return "redirect:success";
                } else {
                    model.addAttribute("errorMessage", String.format("Unable to delete actor %s", actorName));
                    return "error";
                }
            } else {
                model.addAttribute("errorMessage", String.format("Actor %d not found", id));
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @GetMapping("/delete-movie")
    public String deleteMovie(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "delete");
        if (authorized.getAuthorized()){
            if (movieRepository.existsById(id)){
                String actorName = movieRepository.findById(id).get().getTitle();
                movieRepository.delete(movieRepository.findById(id).get());
                if (!movieRepository.existsById(id)){
                    model.addAttribute("successMessage", String.format("Successfully deleted movie %s", actorName));
                    return "redirect:success";
                } else {
                    model.addAttribute("errorMessage", String.format("Unable to delete movie %s", actorName));
                    return "error";
                }
            } else {
                model.addAttribute("errorMessage", String.format("Movie %d not found", id));
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
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
