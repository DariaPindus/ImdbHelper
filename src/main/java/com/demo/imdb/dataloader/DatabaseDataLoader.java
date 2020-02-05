package com.demo.imdb.dataloader;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.MoviePosition;
import com.demo.imdb.model.Person;
import com.demo.imdb.repository.MoviePositionRepository;
import com.demo.imdb.repository.MovieRepository;
import com.demo.imdb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


/*
 * order of data loading :
 * actors -> movies ->
 * */

@Component
public class DatabaseDataLoader implements ApplicationRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MoviePositionRepository moviePositionRepository;

    public void run(ApplicationArguments args) throws Exception {
        loadMovies();
        loadPersons();
        loadMoviePositions();
    }

    public void loadMovies() throws Exception {
        TSVDataSource<Movie> moviesParser = new TSVDataSource<>("/datasource/test_movies.tsv",new MovieParser());
        movieRepository.saveAll(moviesParser.getItems());
    }

    public void loadPersons() throws Exception {
        TSVDataSource<Person> personParser = new TSVDataSource<>("/datasource/test_actors.tsv",
                record -> new Person(record.getString("nconst"), record.getString("primaryName")));
        personRepository.saveAll(personParser.getItems());
    }

    public void loadMoviePositions() throws Exception {
        TSVDataSource<MoviePosition> positions = new TSVDataSource<>("/datasource/test_crew.tsv",
                record -> new MoviePosition(new Person(record.getString("nconst")), new Movie(record.getString("tconst")), record.getString("category")));
        moviePositionRepository.saveAll(positions.getItems());
    }

    private void check() {
        personRepository.findAll();
    }
}