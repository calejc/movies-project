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

//	private final String inputFileName = "src/main/resources/ema_dataset_updated.json";
	private final String inputFileName = "src/main/resources/dataset.json";
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

	private String filename = "src/main/resources/authorized-usernames.txt";
	public Map<String, String> readInAuthorizedUsers(String filename) throws IOException {
		File file = new File(filename);
		Map<String, String> users = new HashMap<>();
		String s;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while((s = br.readLine()) != null){
			String[] splitString = s.split(" ");
			users.put(splitString[0], splitString[1]);
		}
		return users;
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
