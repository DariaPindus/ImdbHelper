package com.demo.imdb.repository;


import com.demo.imdb.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findOneByName(String name);
}
