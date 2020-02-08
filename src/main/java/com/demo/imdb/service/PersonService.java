package com.demo.imdb.service;

import com.demo.imdb.model.Person;
import com.demo.imdb.model.TypecastResult;
import com.demo.imdb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public boolean isPersonTypecasted(String name) {
        List<TypecastResult> typecastResults = personRepository.countMoviesByGenre(name.toLowerCase());
        long totalNumberOfMovies = personRepository.countPersonMovies(name.toLowerCase());
        return typecastResults.stream().anyMatch(it -> it.getCnt() > totalNumberOfMovies / 2);
    }

    @Transactional
    public Iterable<Person> saveAll(Iterable<Person> people) {
        return personRepository.saveAll(people);
    }
}
