package com.demo.imdb.service;

import com.demo.imdb.model.Genre;
import com.demo.imdb.model.Movie;
import com.demo.imdb.model.Person;
import com.demo.imdb.model.TypecastResult;
import com.demo.imdb.repository.MovieRepository;
import com.demo.imdb.repository.PersonRepository;
import com.demo.imdb.service.relationships.PersonRelationshipSearcher;
import com.demo.imdb.service.relationships.RelationshipsLoader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Iterable<Person> saveAll(Iterable<Person> people) {
        return personRepository.saveAll(people);
    }

    @Transactional
    public boolean isPersonTypecasted(String name) {
        List<Movie> movies = movieRepository.findPersonMovie(name.toLowerCase());
        Map<String, Long> genreCounts = movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.groupingBy(Genre::getName, Collectors.counting()));
        return genreCounts.entrySet().stream().anyMatch(pair -> pair.getValue() > (movies.size() / 2));
    }

    @Transactional
    public int KevinBaconSeparationDegrees(String name) {
        Optional<Person> person = personRepository.findFirstByName(name.toLowerCase());
        Optional<Person> kevinBacon = personRepository.findFirstByName("Kevin Bacon".toLowerCase());

        if (!person.isPresent())
            return 0;

        if (!kevinBacon.isPresent())
            throw new KevinBaconNotFoundException();

        PersonRelationshipSearcher searcher =
                new PersonRelationshipSearcher(
                        kevinBacon.get().getId(),
                        parent -> personRepository.getAllCostars(parent).stream().map(Person::getId).collect(Collectors.toList()));
        return searcher.findRelationshipDegree(person.get().getId());
    }

    private class KevinBaconNotFoundException extends IllegalStateException {
    }
}
