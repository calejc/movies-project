package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.transaction.Transactional;
import java.sql.Date;

@Controller
@Transactional
public class ActorsController {

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/actors")
    public String actors(Model model, @RequestParam(defaultValue = "0") int page){
        Pageable sortedByName = PageRequest.of(page, 10, Sort.by("name"));
        model.addAttribute("actors", actorRepository.findAll(sortedByName));
        return "actors";
    }
}

//Generated url format actors?page=1

//    private Long id;
//    private String name, photoUrl;
//    private String biography;
//    private Date birthday, deathday;
//    private Integer gender;
//    private Double popularity;