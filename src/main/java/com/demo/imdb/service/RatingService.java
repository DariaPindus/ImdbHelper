package com.demo.imdb.service;

import com.demo.imdb.model.Movie;
import com.demo.imdb.model.Rating;
import com.demo.imdb.repository.MovieRepository;
import com.demo.imdb.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Rating save(Rating rating) {
        Optional<Movie> persistedMovie = movieRepository.findById(rating.getMovie().getId());
        if (!persistedMovie.isPresent())
            throw new IllegalStateException();
        persistedMovie.get().setRating(rating);
        rating.setMovie(persistedMovie.get());
        return repository.save(rating);
    }
}
