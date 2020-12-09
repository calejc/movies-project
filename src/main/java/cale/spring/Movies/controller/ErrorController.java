package cale.spring.Movies.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {

    @GetMapping("/error")
    public String add(Model model){
        model.addAttribute("pageTitle", "ERROR");
        return "error";
    }
}
