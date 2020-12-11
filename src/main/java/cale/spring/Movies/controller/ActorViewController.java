package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Transactional
public class ActorViewController {

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/actor/{id}")
    public ModelAndView actorView(@PathVariable("id") Long id, ModelAndView mav){
        if (actorRepository.findById(id).isEmpty()){
            mav.setViewName("error");
        } else {
            mav.setViewName("actor");
            String actorName = actorRepository.findById(id).get().getName();
            mav.addObject("actor", actorRepository.findById(id).get());
            mav.addObject("movies", actorRepository.findById(id).get().getMovies());
            mav.addObject("pageTitle", actorName);
//            String slug = actorName.toLowerCase().replace(" ", "-");
//            mav.addObject("actorSlug", slug);
        }
        return mav;
    }
}
