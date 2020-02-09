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

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MoviePositionService moviePositionService;

    @Autowired
    private RatingService ratingService;

    public void run(ApplicationArguments args) throws Exception {
        //loadMovies();
        //loadPersons();
        //loadMoviePositions();
        //loadRating();

        check();
    }

    private void loadMovies() throws Exception {
        TSVDataLoader<Movie> movieLoader = new TSVDataLoader<>(
                "/datasource/title_basics_data.tsv",
                new MovieParser(),
                movie -> movieService.save(movie));
        movieLoader.loadItems();
    }

    private void loadPersons() throws Exception {
        TSVDataLoader<Person> personLoader = new TSVDataLoader<>(
                "/datasource/name_basics_data.tsv",
                record -> new Person(record.getString("nconst"), record.getString("primaryName")),
                person -> personService.save(person));
        personLoader.loadItems();
    }

    private void loadMoviePositions() throws Exception {

        TSVDataLoader<MoviePosition> positions = new TSVDataLoader<>(
                "/datasource/title_principals_data.tsv",
                record -> new MoviePosition(new Person(record.getString("nconst")), new Movie(record.getString("tconst")), record.getString("category")),
                position -> moviePositionService.save(position));
        positions.loadItems();
    }

    private void loadRating() throws Exception {

        TSVDataLoader<Rating> ratingLoader = new TSVDataLoader<>(
                "/datasource/title_ratings_data.tsv",
                record -> new Rating(new Movie(record.getString("tconst")), record.getDouble("averageRating"), record.getLong("numVotes")),
                rating -> ratingService.save(rating));
        ratingLoader.loadItems();
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