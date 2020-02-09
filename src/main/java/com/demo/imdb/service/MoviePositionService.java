package com.demo.imdb.service;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.MoviePosition;
import com.demo.imdb.model.Person;
import com.demo.imdb.repository.MoviePositionRepository;
import com.demo.imdb.repository.MovieRepository;
import com.demo.imdb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MoviePositionService {

    @Autowired
    private MoviePositionRepository moviePositionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public MoviePosition save(MoviePosition moviePosition) {
        Optional<Movie> persistedMovie = movieRepository.findById(moviePosition.getMovie().getId());
        Optional<Person> persistedPerson = personRepository.findById(moviePosition.getPerson().getId());

        if (!persistedMovie.isPresent() || !persistedPerson.isPresent())
            throw new IllegalStateException();

        persistedMovie.get().getCast().add(moviePosition);
        persistedPerson.get().getMovies().add(moviePosition);
        return moviePositionRepository.save(new MoviePosition(moviePosition.getPerson(), persistedMovie.get(), moviePosition.getPosition()));
    }
}
