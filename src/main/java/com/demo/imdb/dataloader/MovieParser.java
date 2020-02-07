package com.demo.imdb.dataloader;

import com.demo.imdb.model.Genre;
import com.demo.imdb.model.Movie;
import com.univocity.parsers.common.record.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieParser implements ModelParser<Movie> {
    @Override
    public Movie parse(Record record) {
        Set<Genre> genres = Arrays.stream(record.getString("genres").split(",")).map(Genre::new).collect(Collectors.toSet());
        return new Movie(record.getString("tconst"), record.getString("primaryTitle"), genres);
    }
}
