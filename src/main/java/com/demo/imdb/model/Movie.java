package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(indexes = {@Index(name = "id_index",  columnList="id", unique = true),
                @Index(name = "name_index", columnList="name", unique = false)})
@NoArgsConstructor
public class Movie {

    @Id
    @Getter @Setter
    private String id;

    @NotBlank
    @Getter @Setter
    @Column(length = 500)
    private String name;

    @OneToMany(mappedBy = "movie")
    @Getter
    private Set<MoviePosition> cast;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Getter @Setter
    private Set<Genre> genres;

    @OneToOne
    @Getter @Setter
    private Rating rating;

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, @NotBlank String name) {
        this.id = id;
        this.name = name;
    }

    public Movie(String id, @NotBlank String name, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
    }
}
