package com.demo.imdb.dataloader;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.MoviePosition;
import com.demo.imdb.model.Person;
import com.demo.imdb.model.Rating;
import com.demo.imdb.service.MoviePositionService;
import com.demo.imdb.service.MovieService;
import com.demo.imdb.service.PersonService;
import com.demo.imdb.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * order of data loading :
 * actors -> movies ->
 * */

@Component
public class DatabaseDataLoader implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(DatabaseDataLoader.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MoviePositionService moviePositionService;

    @Autowired
    private RatingService ratingService;

    public void run(ApplicationArguments args) throws Exception {
        loadMovies();
        loadPersons();
        loadMoviePositions();
        loadRating();

        check();
    }

    private void loadMovies() throws Exception {
        TSVDataSource<Movie> moviesParser = new TSVDataSource<>("/datasource/test_movies.tsv", new MovieParser());
        moviesParser.getItems().forEach(movie -> movieService.save(movie));
    }

    private void loadPersons() throws Exception {
        TSVDataSource<Person> personParser = new TSVDataSource<>("/datasource/test_actors.tsv",
                record -> new Person(record.getString("nconst"), record.getString("primaryName")));
        personService.saveAll(personParser.getItems());
    }

    private void loadMoviePositions() throws Exception {
        TSVDataSource<MoviePosition> positions = new TSVDataSource<>("/datasource/test_crew.tsv",
                record -> new MoviePosition(new Person(record.getString("nconst")), new Movie(record.getString("tconst")), record.getString("category")));
        positions.getItems().forEach(item -> {
            try {moviePositionService.save(item); }
            catch (IllegalStateException e) {
                logger.error("Couldn't save moviePosition for movie " + item.getMovie().getId() + ", postition " + item.getPerson().getId());
            }
        } );
    }

    private void loadRating() throws Exception {
        TSVDataSource<Rating> ratingParser = new TSVDataSource<>("/datasource/test_rating.tsv",
                record -> new Rating(new Movie(record.getString("tconst")), record.getDouble("averageRating"), record.getLong("numVotes")));
        ratingParser.getItems().forEach(item -> {
            try {ratingService.save(item); }
            catch (IllegalStateException e) {
                logger.error("Couldn't save rating for movie " + item.getMovie().getId());
            }
        } );
    }

    private void check() {
        //List<Person> people = personService.findAll();
        //List<MoviePosition>  moviePositions = moviePositionRepository.findByPersonMovie("Fred Astaire");
        List<Movie> result = movieService.findPersonMovie("Fred Astaire");
        //List<Movie> rated = movieService.findTopRatedMoviesByGenre("Short");
        //boolean typecasted = personService.isPersonTypecasted("Fred Astaire");
        int degree = personService.KevinBaconSeparationDegrees("Richard Burton");
        System.out.println("");
    }
}