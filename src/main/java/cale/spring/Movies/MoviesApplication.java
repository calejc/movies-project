package cale.spring.Movies;

import cale.spring.Movies.model.Actor;
import cale.spring.Movies.model.Movie;
import cale.spring.Movies.service.MovieService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MoviesApplication implements CommandLineRunner {

	@Autowired
	MovieService movieService;

	private final String inputFileName = "src/main/resources/test_output.json";
	Integer counter = 0;

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		importDataset(inputFileName);
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

	public void importDataset(String filename) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Movie[] movies = objectMapper.readValue(new File(filename), Movie[].class);
		movieService.addMovie(movies);
		for (Movie movie : movies){
			for (Actor actor : movie.getActors()){
				addToCounter();
				System.out.println(counter);
			}
		}
	}

}
