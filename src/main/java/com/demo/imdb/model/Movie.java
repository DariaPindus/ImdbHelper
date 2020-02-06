package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Movie {

    @Id
    @Getter @Setter
    private String id;

    @NotBlank
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "movie")
    @Getter
    private Set<MoviePosition> cast;

    @Getter @Setter
    private String genres;

    @OneToOne(mappedBy = "movie")
    @Getter @Setter
    private Rating rating;

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, @NotBlank String name, String genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
    }
}