package cale.spring.Movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UpdateController {

    @GetMapping("/update")
    public String add(Model model){
        model.addAttribute("pageTitle", "Update database");
        return "update";
    }

}
