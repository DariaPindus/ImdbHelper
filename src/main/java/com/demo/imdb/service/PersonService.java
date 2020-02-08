package com.demo.imdb.service;

import com.demo.imdb.model.Person;
import com.demo.imdb.model.TypecastResult;
import com.demo.imdb.repository.PersonRepository;
import com.demo.imdb.service.relationships.PersonRelationshipSearcher;
import com.demo.imdb.service.relationships.RelationshipsLoader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Iterable<Person> saveAll(Iterable<Person> people) {
        return personRepository.saveAll(people);
    }

    @Transactional
    public boolean isPersonTypecasted(String name) {
        List<TypecastResult> typecastResults = personRepository.countMoviesByGenre(name.toLowerCase());
        long totalNumberOfMovies = personRepository.countPersonMovies(name.toLowerCase());
        return typecastResults.stream().anyMatch(it -> it.getCnt() > totalNumberOfMovies / 2);
    }

    @Transactional
    public int KevinBaconSeparationDegrees(String name) {
        Optional<Person> person = personRepository.findOptionalByName(name.toLowerCase());
        Optional<Person> kevinBacon = personRepository.findOptionalByName("Kevin Bacon".toLowerCase());

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

    private class KevinBaconNotFoundException extends IllegalStateException {}
}
