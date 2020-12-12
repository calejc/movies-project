package cale.spring.Movies.controller;

import cale.spring.Movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public Model actors(Model model){
        model.addAttribute("pageTitle", "Actors");

        return model;
    }
}



//    private Long id;
//    private String name, photoUrl;
//    private String biography;
//    private Date birthday, deathday;
//    private Integer gender;
//    private Double popularity;