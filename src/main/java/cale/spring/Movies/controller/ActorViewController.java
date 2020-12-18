package cale.spring.Movies.controller;

import cale.spring.Movies.authorization.Authorized;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller
@Transactional
public class ActorViewController {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    AuthorizationService authorizationService;


    @GetMapping("/actor/{id}")
    public ModelAndView actorView(@PathVariable("id") Long id, ModelAndView mav, Principal principal){
        Authorized authorized = authorizationService.authorized(principal);
        mav.addObject("delete", authorized.getDelete());
        mav.addObject("update", authorized.getUpdate());
        if (actorRepository.findById(id).isEmpty()){
            mav.setViewName("error");
            String errorMessage = String.format("Actor %d not found", id);
            mav.addObject("errorMessage", errorMessage);
        } else {
            mav.setViewName("actor");
            String actorName = actorRepository.findById(id).get().getName();
            mav.addObject("actor", actorRepository.findById(id).get());
            mav.addObject("pageTitle", actorName);
        }
        return mav;
    }
}
