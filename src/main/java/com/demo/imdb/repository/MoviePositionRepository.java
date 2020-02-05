package com.demo.imdb.repository;

import com.demo.imdb.model.MoviePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePositionRepository extends JpaRepository<MoviePosition, Long> {
}
