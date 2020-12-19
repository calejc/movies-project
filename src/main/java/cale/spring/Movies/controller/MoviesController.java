package cale.spring.Movies.controller;

import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.MovieRepository;
import cale.spring.Movies.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Transactional
public class MoviesController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    PageService pageService;

    @GetMapping("/movie")
    public String movies(Model model,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        boolean firstPage = false;
        if (currentPage == 1) {
            firstPage = true;
        }


        Page<Movie> moviePage = pageService.findPaginatedMovies(PageRequest.of(currentPage - 1,
                pageSize));
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("currentPage", currentPage);
        int totalPages = moviePage.getTotalPages();
        System.out.println(totalPages);
        boolean lastPage = false;
        if (currentPage == totalPages) {
            lastPage = true;
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageRange", buildPageRange(currentPage, totalPages));

        if (currentPage > totalPages) {
            model.addAttribute("errorMessage", "Page not found");
            return "error";
        }

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "movies";
    }

    public List<Integer> buildPageRange(int currentPage, int totalPages) {
        if (currentPage == 1) {
            return List.of(currentPage, currentPage + 1, currentPage + 2, currentPage + 3, currentPage + 4);
        } else if (currentPage == 2) {
            return List.of(currentPage - 1, currentPage, currentPage + 1, currentPage + 2, currentPage + 3);
        } else if (currentPage == totalPages - 1) {
            return List.of(currentPage - 3, currentPage - 2, currentPage - 1, currentPage, currentPage + 1);
        } else if (currentPage == totalPages) {
            return List.of(currentPage - 4, currentPage - 3, currentPage - 2, currentPage - 1, currentPage);
        } else {
            return List.of(currentPage - 2, currentPage - 1, currentPage, currentPage + 1, currentPage + 2);
        }
    }
}

//    @GetMapping("/movies")
//    public String movies(Model model, @RequestParam(defaultValue = "0") int page){
//        Pageable sortedByTitle = PageRequest.of(page, 10, Sort.by("title"));
//        model.addAttribute("movies", movieRepository.findAll(sortedByTitle));
//        return "movies";
//    }


//Generated url format moviess?page=1
