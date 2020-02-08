package com.demo.imdb.repository;

import com.demo.imdb.model.Person;
import com.demo.imdb.model.TypecastResult;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Override
    Optional<Person> findById(String s);

    Optional<Person> findOptionalByName(String name);

    @Query("select new com.demo.imdb.model.TypecastResult(g.name, count(p.id))" +
            "from Person p join p.movies mp join mp.movie m join m.genres g " +
            " where p.name like %?1% group by g.name ")
    List<TypecastResult> countMoviesByGenre(String name);

    @Query("select count(m.movie) from Person p join p.movies m where p.name like %?1%")
    long countPersonMovies(String name);

    @Query("select distinct p from Person p join p.movies pm where pm.movie in (select mp.movie from MoviePosition mp where mp.person.id=?1)")
    List<Person> getAllCostars(String id);
}
