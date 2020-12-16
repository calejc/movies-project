package cale.spring.Movies.controller;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Transactional
public class ActorsController {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    PageService pageService;

    @GetMapping("/actor")
    public String actors(Model model,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size")Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Actor> actorPage = pageService.findPaginated(PageRequest.of(currentPage - 1,
                pageSize));
        model.addAttribute("actorPage", actorPage);
        int totalPages = actorPage.getTotalPages();
        System.out.println(totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "actors";
    }

//    Generated URL format ?page=2&size=6

//    @GetMapping("/actor")
//    public String actors(Model model,
//                         @RequestParam(defaultValue = "0") int page){
//        Pageable sortedByName = PageRequest.of(page, 10, Sort.by("name"));
//        model.addAttribute("actors", actorRepository.findAll(sortedByName));
//        return "actors";
//    }
}

//Generated url format actors?page=1
