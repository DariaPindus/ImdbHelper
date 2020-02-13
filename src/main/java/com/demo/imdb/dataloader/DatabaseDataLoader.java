package com.demo.imdb.dataloader;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.MoviePosition;
import com.demo.imdb.model.Person;
import com.demo.imdb.model.Rating;
import com.demo.imdb.service.MoviePositionService;
import com.demo.imdb.service.MovieService;
import com.demo.imdb.service.PersonService;
import com.demo.imdb.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

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
        loadMovies();
        loadPersons();
        loadMoviePositions();
        loadRating();

        //check();
    }

    private void loadMovies() throws Exception {
        TSVDataLoader<Movie> movieLoader = new TSVDataLoader<>(
                "/datasource/test_movies.tsv",
                new MovieParser(),
                movie -> movieService.save(movie));
        movieLoader.loadItems();
    }

    private void loadPersons() throws Exception {
        TSVDataLoader<Person> personLoader = new TSVDataLoader<>(
                "/datasource/test_actors.tsv",
                record -> new Person(record.getString("nconst"), record.getString("primaryName")),
                person -> personService.save(person));
        personLoader.loadItems();
    }

    private void loadMoviePositions() throws Exception {

        TSVDataLoader<MoviePosition> positions = new TSVDataLoader<>(
                "/datasource/test_crew.tsv",
                record -> new MoviePosition(new Person(record.getString("nconst")), new Movie(record.getString("tconst")), record.getString("category")),
                position -> moviePositionService.save(position));
        positions.loadItems();
    }

    private void loadRating() throws Exception {

        TSVDataLoader<Rating> ratingLoader = new TSVDataLoader<>(
                "/datasource/test_rating.tsv",
                record -> new Rating(new Movie(record.getString("tconst")), record.getDouble("averageRating"), record.getLong("numVotes")),
                rating -> ratingService.save(rating));
        ratingLoader.loadItems();
    }

    private void check() {
        executeElapsed("checkTopRated" , () -> {
            List<Movie> genred = movieService.findTopRatedMoviesByGenre("animation");
            System.out.println("");
        });

        executeElapsed("typecasted", () -> {
            boolean res = personService.isPersonTypecasted("Brigitte Bardot");
            System.out.println("");
        });
        executeElapsed("separationDegree", () -> {
            int degree = personService.KevinBaconSeparationDegrees("Brigitte Bardot");
            System.out.println("");
        });

    }

    interface ElapseFunction {
        void execute() throws Exception;
    }

    private void executeElapsed(String name, ElapseFunction function) {
        long start = System.nanoTime();
        try {
            function.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println(name + " Ran in " + (end - start) + " ns");
    }
}