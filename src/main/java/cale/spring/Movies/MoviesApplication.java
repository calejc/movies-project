package cale.spring.Movies;

import cale.spring.Movies.Models.Actor;
import cale.spring.Movies.Models.Movie;
import cale.spring.Movies.Services.MovieService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MoviesApplication implements CommandLineRunner {

	@Autowired
	MovieService movieService;

	private final String inputFileName = "src/main/resources/ema_dataset_updated.json";

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
//		importDataset(inputFileName);
		temp();
	}

	public void temp(){

		Movie movie = new Movie();
		movie.setTitle("Test Movie");
		Set<Actor> actors = new HashSet<>();
		Actor actor1 = new Actor();
		Actor actor2 = new Actor();
		actor1.setName("Test Actor 1");
		actor2.setName("Test Actor 2");
		actors.add(actor1);
		actors.add(actor2);
		movie.setActors(actors);
		movieService.addMovie(movie);
	}

	public void importDataset(String filename) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Movie[] movies = objectMapper.readValue(new File(filename), Movie[].class);
		for (Movie movie : movies){
			movieService.addMovie(movie);
			System.out.println(movie.getTitle());
			for (Actor actor : movie.getActors()){
				System.out.println("\t" + actor.getName());
			}
			break;
		}
	}

}
