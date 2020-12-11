package cale.spring.Movies.controller;

import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MovieViewController {
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/movie/{id}")
    public ModelAndView movieView(@PathVariable("id") Long id, ModelAndView mav){
        if (movieRepository.findById(id).isEmpty()){
            mav.setViewName("error");

        } else {
            mav.setViewName("movie");
            String movieTitle = movieRepository.findById(id).get().getTitle();
            mav.addObject("movie", movieRepository.findById(id).get());
            mav.addObject("pageTitle", movieTitle);
        }
        return mav;
    }
}
