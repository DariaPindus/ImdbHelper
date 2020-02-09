package com.demo.imdb.service;

import com.demo.imdb.model.Genre;
import com.demo.imdb.model.Movie;
import com.demo.imdb.repository.GenreRepository;
import com.demo.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final static Pageable TOP_RATED_PAGE_REQUEST = PageRequest.of(0, 10);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public Movie save(Movie movie) {
        Set<Genre> persistedGenres =
                movie.getGenres().stream()
                        .filter(genre -> !genre.getName().equals("\\N"))
                        .map(genre -> genreRepository.findOptionalByName(genre.getName()).orElse(genre))
                        .collect(Collectors.toSet());
        movie.setGenres(persistedGenres);
        return movieRepository.save(movie);
    }

    @Transactional
    public List<Movie> findTopRatedMoviesByGenre(String genre) {
        return movieRepository.findTopRatedMoviesByGenre(genre.toLowerCase(), TOP_RATED_PAGE_REQUEST);
    }
}
