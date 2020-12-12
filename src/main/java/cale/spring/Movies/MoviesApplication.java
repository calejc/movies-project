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

	private final String inputFileName = "src/main/resources/ema_dataset_updated.json";
	Integer counter = 0;

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		importDataset(inputFileName);
//		temp();
	}

	public void temp(){
//		Movie movie = new Movie();
//		movie.setTitle("Test Movie");
//		Set<Actor> actors = new HashSet<>();
//		Actor actor1 = new Actor();
//		Actor actor2 = new Actor();
//		actor1.setName("Test Actor 1");
//		actor2.setName("Test Actor 2");
//		actors.add(actor1);
//		actors.add(actor2);
//		movie.setActors(actors);
//		movieService.addMovie(movie);
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public void addToCounter(){
		Integer c = getCounter();
		c++;
		setCounter(c);
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
