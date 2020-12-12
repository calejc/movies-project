package cale.spring.Movies;

import cale.spring.Movies.dto.ActorDTO;
import cale.spring.Movies.dto.MovieDTO;
import cale.spring.Movies.service.MovieService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MoviesApplication implements CommandLineRunner {

	@Autowired
	MovieService movieService;

	private final String inputFileName = "src/main/resources/test_output.json";

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		importDataset(inputFileName);
	}


	// This should create DTOs since entities (aka models here) are only to be used
	// by the service and repository layers.
	public void importDataset(String filename) throws IOException {
		Map<ActorDTO, List<MovieDTO>> actorToMovieMap = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MovieDTO[] movies = objectMapper.readValue(new File(filename), MovieDTO[].class);
		for (MovieDTO movie : movies){
		    // Add one movie at a time like the example (movie is like a URL in the example)
			movieService.addMovie(movie);
			/*
			for (ActorDTO actor : movie.getActors()){
				addToMap(actorToMovieMap, actor, movie); // Detects when actor is in more than one movie
		Movie[] movies = objectMapper.readValue(new File(filename), Movie[].class);
		movieService.addMovie(movies);
		for (Movie movie : movies){
			for (Actor actor : movie.getActors()){
				addToCounter();
				System.out.println(counter);
			}
		 */
		}
	}

	private void addToMap(Map<ActorDTO, List<MovieDTO>> actorToMovieMap, ActorDTO actor, MovieDTO movie) {
		List<MovieDTO> movieList = actorToMovieMap.get(actor);
		if (movieList==null) {
			movieList=new ArrayList<>();
		}
		movieList.add(movie);
		actorToMovieMap.put(actor, movieList);
		if (movieList.size()>1) {
			System.out.format("%s (%d) is in %d movies.\n",actor.getName(), actor.getActorId(), movieList.size());
			System.out.println("Well?");
		}
	}

}
