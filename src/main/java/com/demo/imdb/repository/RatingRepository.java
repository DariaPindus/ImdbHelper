package com.demo.imdb.repository;

import com.demo.imdb.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TODO: nyzhen voobzhe?
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
