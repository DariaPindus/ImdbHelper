package com.demo.imdb.repository;

import com.demo.imdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Override
    Optional<Movie> findById(String s);
}
