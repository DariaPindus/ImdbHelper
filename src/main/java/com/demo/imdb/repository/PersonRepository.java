package com.demo.imdb.repository;

import com.demo.imdb.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Override
    Optional<Person> findById(String s);
}
