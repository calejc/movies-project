package cale.spring.Movies.service;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.repository.ActorRepository;
import cale.spring.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PageService {

    @Autowired
    ActorRepository actorRepository;
    @Autowired
    MovieRepository movieRepository;



    public Page<Actor> findPaginatedActors(Pageable pageable) {
        List<Actor> actors = actorRepository.findAllByOrderByName();
//        for(Actor actor : actors){
//            System.out.println(actor);
//        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Actor> list;

        if (actors.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, actors.size());
            list = actors.subList(startItem, toIndex);
        }

        return new PageImpl<Actor>(list, PageRequest.of(currentPage, pageSize), actors.size());
    }

    public Page<Movie> findPaginatedMovies(Pageable pageable) {
        List<Movie> movies = movieRepository.findAllByOrderByTitle();
//        for(Actor actor : actors){
//            System.out.println(actor);
//        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Movie> list;

        if (movies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, movies.size());
            list = movies.subList(startItem, toIndex);
        }

        return new PageImpl<Movie>(list, PageRequest.of(currentPage, pageSize), movies.size());
    }
}
