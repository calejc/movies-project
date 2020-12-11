package cale.spring.Movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Tuple;
import java.security.Principal;
import java.util.Map;

@Controller
public class UpdateController {

    private Map<String, String> authorizationMap;

    @GetMapping("/update")
    public String add(Model model, Principal princpal){
        String userName = getUsername(princpal);
        String authorizations = authorizationMap.get(userName); //create,update
        if (authorizations.indexOf("update")>= 0) {
            model.addAttribute("pageTitle", "Update database");
            return "update";
        } else {
            return "GO AWAY";
        }
    }

    @GetMapping("/create")
    public String create(Model model, Principal princpal){
        String userName = getUsername(princpal);
        String authorizations = authorizationMap.get(userName); //create,update
        if (authorizations.indexOf("create")>= 0) {
            model.addAttribute("pageTitle", "Update database");
            return "updatee";
        } else {
            model.addAttribute("");
            return "probably jordan's fault";
        }
    }


    @GetMapping("/delete")
    public String delete(Model model, Principal princpal){
        String userName = getUsername(princpal);
        String authorizations = authorizationMap.get(userName); //create,update
        if (authorizations.indexOf("delete")>= 0) {
            model.addAttribute("pageTitle", "Update database");
            return "update";
        } else {
            return "GO AWAY";
        }
    }

    private String getUsername(Principal princpal) {
        return "joe";
    }

    @PostMapping("/success")
    public String update(@RequestBody Model model) {
        model.getAttribute("actorName");
        model.getAttribute("movieName");
        model.getAttribute("movieGenre");
        model.getAttribute("movieRating");
        model.getAttribute("moviePhotoUrl");
        return "success";
    }

}
