package cale.spring.Movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model){
        String currDate = (new Date()).toString();
        model.addAttribute("currDate", currDate);
        model.addAttribute("pageTitle", "About");
        return "about";
    }
}
