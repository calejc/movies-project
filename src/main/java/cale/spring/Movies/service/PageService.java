package cale.spring.Movies.service;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.model.Actor;
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



    public Page<Actor> findPaginated(Pageable pageable) {
        List<Actor> actors = actorRepository.findAll();
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
}
