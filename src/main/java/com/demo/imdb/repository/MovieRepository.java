package com.demo.imdb.repository;

import com.demo.imdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Override
    Optional<Movie> findById(String s);

    @Query("select m from Movie m join m.genres g join m.rating r where g.name like %?1% order by r.averageRating desc ")
    List<Movie> findTopRatedMoviesByGenre(String genre);

    @Query("select m from Movie m join m.cast c join c.person p where p.name like %?1% ")
    List<Movie> findPersonMovie(String name);
}
