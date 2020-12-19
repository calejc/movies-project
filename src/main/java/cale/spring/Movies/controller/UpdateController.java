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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class UpdateController {

    @Autowired
    CrudService crudService;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    AuthorizationService authorizationService;


    @GetMapping("/update")
    public String returnContributePage(Model model, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getUpdate() || authorized.getDelete() || authorized.getCreate()){
            model.addAttribute("pageTitle", "Update");
            return "update";
        } else {
            model.addAttribute("errorMessage", authorized.getReturnMessage());
            return "error";
        }
    }

    @PostMapping("/add-movie")
    public ModelAndView addMovie(@RequestParam("title") String title, @RequestParam("overview") String overview, ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getCreate()) {
            MovieDTO movie = new MovieDTO(crudService.generateNewMovieId(), title, overview);
            Movie savedMovie = crudService.addMovieToDB(movie);
            mav.addObject("successMessage", String.format("%s saved successfully",savedMovie.getTitle()));
            mav.setViewName(String.format("redirect:edit-movie/actors?id=%d", savedMovie.getId()));
        } else {
            mav.addObject("errorMessage", authorized.getReturnMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    @PostMapping("/add-actor")
    public ModelAndView addActor(@RequestParam("name") String name, @RequestParam("biography") String biography,  ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getCreate()) {
            ActorDTO actor = new ActorDTO(crudService.generateNewActorId(), name, biography);
            Actor savedActor = crudService.addActorToDB(actor);
            mav.addObject("successMessage", String.format("%s saved successfully",savedActor.getName()));
            mav.setViewName(String.format("redirect:edit-actor/movies?id=%d", savedActor.getId()));
        } else {
            mav.addObject("errorMessage", authorized.getReturnMessage());
        }
        return mav;
    }


    @GetMapping("/edit-actor")
    public String editActorForm(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getUpdate()){
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
    public ModelAndView editMovieForm(@RequestParam("id") Long id, ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getUpdate()){
            if (movieRepository.findById(id).isPresent()){
                Movie movie = movieRepository.findById(id).get();
                mav.addObject("movie", movie);
                mav.setViewName("edit-movie");
            } else {
                mav.addObject("errorMessage", String.format("Movie %d not found", id));
                mav.setViewName("error");
            }
        } else {
            mav.addObject("errorMessage", authorized.getReturnMessage());
            mav.setViewName("error");
        }
        return mav;
    }

    @PostMapping("/edit-movie")
    public ModelAndView editMovie(@RequestParam Long id, @RequestParam String title, ModelAndView mav){
        System.out.println(title);
        System.out.println(id);
        movieRepository.updateMovieTitleById(title, id);
//        movieRepository.updateMovieOverviewById(overview, id);
        mav.setViewName(String.format("redirect:edit-movie/actors?id=%d", id));
        return mav;
    }

    @GetMapping("/edit-movie/actors")
    public ModelAndView editMoviesActors(@RequestParam Long id, ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getUpdate()){
            mav.addObject("movieId", id);
            mav.setViewName("add-actors");
        } else {
            mav.addObject("errorMessage", authorized.getReturnMessage());
        }
         return mav;
    }


    @RequestMapping(params = "formAction=save", value = "/edit-movie/actors", method=RequestMethod.POST)
    public ModelAndView editMoviesActorsSave(@RequestParam Map<String, String> allParams, ModelAndView mav){
        // Create movieDTO
        Long movieId = Long.parseLong(allParams.get("id"));
        Movie movie = new Movie();
        if (movieRepository.findById(movieId).isPresent()){
            movie = movieRepository.findById(movieId).get();
        }
        MovieDTO movieDTO = convertMovieToDTO(movie);
        System.out.println(movieDTO);

        // Create set of actorDTOs from our form
        Set<ActorDTO> newActorDTOs = new HashSet<>();
        List<String> nonActorParams = List.of("id", "q", "formAction");
        for (Map.Entry<String, String> entry : allParams.entrySet()){
            if (!nonActorParams.contains(entry.getKey()) && actorRepository.findById(Long.parseLong(entry.getKey())).isPresent()){
                Actor actor = actorRepository.findById(Long.parseLong(entry.getKey())).get();
                ActorDTO actorDTO = new ActorDTO(actor.getId(), actor.getName(), actor.getBiography());
                newActorDTOs.add(actorDTO);
            }
        }

        // save
        for (ActorDTO actorDTO : newActorDTOs){
            crudService.addMovieToActor(movieDTO, actorDTO);
        }

        mav.addObject("movie", movieRepository.findById(movieId));
        mav.setViewName(String.format("redirect:/edit-movie/actors/confirm?id=%d", movieId));
        return mav;
    }

    @RequestMapping(params = "formAction=search", value = "/edit-movie/actors", method=RequestMethod.POST)
    public ModelAndView editMoviesActorsSearch(@RequestParam Map<String, String> allParams, ModelAndView mav){
        List<String> nonActorParams = List.of("id", "q", "formAction");
        List<Actor> actorsAdded = new ArrayList<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()){
            if (!nonActorParams.contains(entry.getKey()) && actorRepository.findById(Long.parseLong(entry.getKey())).isPresent()){
                Actor actor = actorRepository.findById(Long.parseLong(entry.getKey())).get();
                actorsAdded.add(actor);
            }
        }
        List<Actor> actors = actorRepository.findByNameContainingIgnoreCase(allParams.get("q"));
        mav.addObject("movieId", allParams.get("id"));
        mav.addObject("actorsAdded", actorsAdded);
        mav.addObject("actors", actors);
        mav.setViewName("add-actors");
        return mav;
    }

    @RequestMapping(params = "formAction=add", value = "/edit-movie/actors", method=RequestMethod.POST)
    public ModelAndView editMoviesActorsAdd(@RequestParam Map<String, String> allParams, ModelAndView mav){
        List<String> nonActorParams = List.of("id", "q", "formAction");
        List<Actor> actorsAdded= new ArrayList<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()){
            if (!nonActorParams.contains(entry.getKey()) && actorRepository.findById(Long.parseLong(entry.getKey())).isPresent()){
                Actor actor = actorRepository.findById(Long.parseLong(entry.getKey())).get();
                actorsAdded.add(actor);
            }
        }
        mav.addObject("movieId", allParams.get("id"));
        mav.addObject("actorsAdded", actorsAdded);
        mav.setViewName("add-actors");
        return mav;
    }

    @GetMapping("/edit-movie/actors/confirm")
    public ModelAndView confirmMoviesActors(@RequestParam("id") Long id, ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getUpdate()){
            if (movieRepository.findById(id).isPresent()){
                Movie movie = movieRepository.findById(id).get();
                mav.addObject("movie", movie);
                mav.setViewName("redirect:/success");
            } else {
                mav.addObject("errorMessage", String.format("Movie %d not found", id));
                mav.setViewName("error");
            }
        } else {
            mav.addObject("errorMessage", authorized.getReturnMessage());
            mav.setViewName("error");
        }
        return mav;
    }


    //TODO
    // Remove movie from actors when deselected
//    @PostMapping("/edit-movie/actors/confirm")
//    public ModelAndView confirmMoviesActors(@RequestParam("id") String id, @RequestParam Map<String, String> allParams, ModelAndView mav, Principal principal){
//
//        Authorized authorized = authorized((String) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes().get("login"), "update");
//
//
//        if (authorized.getAuthorized()){
//
//            Long movieId = Long.parseLong(allParams.get("id"));
//            Movie movie = new Movie();
//            Set<Actor> updatedActors = new HashSet<>();
//
//            if (movieRepository.findById(movieId).isPresent()){
//                movie = movieRepository.findById(movieId).get();
//            } else {
//                mav.addObject("errorMessage", String.format("Movie %d not found", id));
//                mav.setViewName("error");
//                return mav;
//            }
//            for (Map.Entry<String, String> entry : allParams.entrySet()){
//                if (!entry.getKey().equals("id") && actorRepository.findById(Long.parseLong(entry.getKey())).isPresent()){
//                    Actor actor = actorRepository.findById(Long.parseLong(entry.getKey())).get();
//                    updatedActors.add(actor);
//                }
//            }
//            Set<Actor> previousListOfActors = movie.getActors();
//            for (Actor actor : previousListOfActors){
//                if (!updatedActors.contains(actor) && actorRepository.findById(actor.getId()).isPresent()){
//                    Actor a = actorRepository.findById(actor.getId()).get();
//                    a.removeMovie(movie);
//                    actorRepository.save(a);
//                }
//            }
//            Movie updatedMovie = movieRepository.findById(movieId).get();
//            for (Actor actor : updatedMovie.getActors()){
//                System.out.printf("%s\n", actor.getName());
//            }
//            if (updatedActors.size() == updatedMovie.getActors().size() && movie.getActors().containsAll(updatedActors)){
//                mav.addObject("successMessage", updatedMovie.getActors());
//                mav.setViewName("success");
//            } else {
//                mav.addObject("errorMessage", "ACTOR UPDATE UNSUCCESSFUL");
//                mav.setViewName("error");
//            }
//        } else {
//            mav.addObject("errorMessage", authorized.getReturnMessage());
//            mav.setViewName("error");
//        }
//        return mav;
//    }




    // ---------------- //
    //    Delete txs    //
    // ---------------- //
    @GetMapping("/delete-actor")
    public String deleteActor(@RequestParam("id") Long id, Model model, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getDelete()){
            if (actorRepository.existsById(id)){
                String actorName = actorRepository.findById(id).get().getName();
                actorRepository.delete(actorRepository.findById(id).get());
                if (!actorRepository.existsById(id)){
                    model.addAttribute("successMessage", String.format("Successfully deleted actor %s", actorName));
                    return "success";
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
        Authorized authorized = authorizationService.authorized(principal);
        if (authorized.getDelete()){
            if (movieRepository.existsById(id)){
                String movieTitle = movieRepository.findById(id).get().getTitle();
//                movieRepository.delete(movieRepository.findById(id).get());
                movieRepository.deleteById(id);
                if (!movieRepository.existsById(id)){
                    model.addAttribute("successMessage", String.format("Successfully deleted movie %s", movieTitle));
                    return "success";
                } else {
                    model.addAttribute("errorMessage", String.format("Unable to delete movie %s", movieTitle));
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



    // ------------------------ //
    //    Successful tx view    //
    // ------------------------ //
    @GetMapping("/success")
    public String successfulAction(Model model){
        return "success";
    }



    // ----------------------- //
    //    Utility functions    //
    // ----------------------- //
    public static RedirectView safeRedirect(String url){
        RedirectView rv = new RedirectView(url);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    public MovieDTO convertMovieToDTO(Movie movie){
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getPhotoUrl(), movie.getOverview(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getPopularity(), convertActorSetToDto(movie.getActors()));
    }

    public Set<ActorDTO> convertActorSetToDto(Set<Actor> actors){
        Set<ActorDTO> actorDTOs = new HashSet<>();
        for (Actor actor : actors){
            actorDTOs.add(convertActorToDto(actor));
        }
        return actorDTOs;
    }


    public ActorDTO convertActorToDto(Actor actor){
        return new ActorDTO(actor.getId(), actor.getName(), actor.getPhotoUrl(), actor.getBiography(), actor.getBirthday(), actor.getDeathday(), actor.getGender(), actor.getPopularity());
    }

}
