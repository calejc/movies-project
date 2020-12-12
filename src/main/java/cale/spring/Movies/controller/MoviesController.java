package cale.spring.Movies.controller;

import cale.spring.Movies.repository.MovieRepository;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class MoviesController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/movies")
    public String movies(Model model, @RequestParam(defaultValue = "0") int page){
        Pageable sortedByTitle = PageRequest.of(page, 10, Sort.by("title"));
        model.addAttribute("movies", movieRepository.findAll(sortedByTitle));
        return "movies";
    }
}

//Generated url format moviess?page=1
