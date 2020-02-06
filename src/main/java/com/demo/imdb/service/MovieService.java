package com.demo.imdb.service;

import com.demo.imdb.model.Movie;
import com.demo.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findTopRatedMoviesByGenre(String genre) {
        return movieRepository.findTopRatedMoviesByGenre(genre.toLowerCase());
    }
}
