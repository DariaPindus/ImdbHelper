package com.demo.imdb.repository;

import com.demo.imdb.model.MoviePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviePositionRepository extends JpaRepository<MoviePosition, Long> {

    @Query("select mv from MoviePosition mv where mv.person.name like %?1% ")
    List<MoviePosition> findByPersonMovie(String name);
}
