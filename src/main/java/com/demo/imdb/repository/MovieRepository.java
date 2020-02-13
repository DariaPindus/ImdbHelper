package com.demo.imdb.repository;

import com.demo.imdb.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Override
    Optional<Movie> findById(String s);

    //@Query("select m from Movie m join m.genres g join m.rating r where g.name like %?1% order by r.averageRating desc ")
    @Query(value = "select * from movie m join movie_genres mg on m.id = mg.movie_id join genre g on g.id=mg.genres_id join rating r on m.rating_id=r.id where " +
            "g.name=?1 order by r.average_rating desc limit 10",
    nativeQuery = true)
    List<Movie> findTopRatedMoviesByGenre(String genre);

    @Query("select m from Movie m join m.cast c join c.person p where p.name like %?1% ")
    List<Movie> findPersonMovie(String name);
}
