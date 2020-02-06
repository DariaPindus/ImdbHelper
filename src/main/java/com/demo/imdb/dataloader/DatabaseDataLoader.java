package com.demo.imdb.dataloader;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.MoviePosition;
import com.demo.imdb.model.Person;
import com.demo.imdb.repository.MoviePositionRepository;
import com.demo.imdb.repository.MovieRepository;
import com.demo.imdb.repository.PersonRepository;
import com.demo.imdb.service.MoviePositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/*
 * order of data loading :
 * actors -> movies ->
 * */

@Component
public class DatabaseDataLoader implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(DatabaseDataLoader.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MoviePositionService moviePositionService;

    public void run(ApplicationArguments args) throws Exception {
        loadMovies();
        loadPersons();
        loadMoviePositions();
    }

    private void loadMovies() throws Exception {
        TSVDataSource<Movie> moviesParser = new TSVDataSource<>("/datasource/test_movies.tsv",
                record -> new Movie(record.getString("tconst"), record.getString("primaryTitle"), record.getString("genres")));
        movieRepository.saveAll(moviesParser.getItems());
    }

    private void loadPersons() throws Exception {
        TSVDataSource<Person> personParser = new TSVDataSource<>("/datasource/test_actors.tsv",
                record -> new Person(record.getString("nconst"), record.getString("primaryName")));
        personRepository.saveAll(personParser.getItems());
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

    private void check() {
        personRepository.findAll();
    }
}