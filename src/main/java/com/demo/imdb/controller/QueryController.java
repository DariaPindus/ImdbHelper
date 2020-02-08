package com.demo.imdb.controller;

import com.demo.imdb.model.Movie;
import com.demo.imdb.service.MovieService;
import com.demo.imdb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QueryController extends com.demo.imdb.controller.RestController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @GetMapping("/movies/top-rated")
    public List<String> getTopRatedMoviesForGenre(@RequestParam(value = "genre") String genre) {
        assertParameterNotNull("genre", genre);

        return movieService.findTopRatedMoviesByGenre(genre).stream().map(Movie::getName).collect(Collectors.toList());
    }

    //TODO: as well can the genre of typecasting can be returned, depends on requirements
    @GetMapping("/person/typecasted")
    public boolean isPersonTypecasted(@RequestParam(value = "name") String name) {
        assertParameterNotNull("name", name);

        return personService.isPersonTypecasted(name);
    }

    @GetMapping("/person/degree-to-kb")
    public int findKevinBaconSeparationDegrees(@RequestParam(value = "name") String name) {
        assertParameterNotNull("name", name);

        return personService.KevinBaconSeparationDegrees(name);
    }
}
