package com.demo.imdb.service;

import com.demo.imdb.model.Genre;
import com.demo.imdb.model.Movie;
import com.demo.imdb.repository.GenreRepository;
import com.demo.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public Movie save(Movie movie) {
        Set<Genre> persistedGenres =
                movie.getGenres().stream()
                        .map(genre -> genreRepository.findOptionalByName(genre.getName()).orElse(genre))
                        .collect(Collectors.toSet());
        movie.setGenres(persistedGenres);
        return movieRepository.save(movie);
    }

    //TODO: slice results
    public List<Movie> findTopRatedMoviesByGenre(String genre) {
        return movieRepository.findTopRatedMoviesByGenre(genre.toLowerCase());
    }

    public List<Movie> findPersonMovie(String name){
        return movieRepository.findPersonMovie(name.toLowerCase());
    }
}
