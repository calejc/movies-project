package cale.spring.Movies.controller;

import cale.spring.Movies.model.Genre;
import cale.spring.Movies.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@Transactional
public class GenreController {

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/genres")
    public String genre(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }



}
