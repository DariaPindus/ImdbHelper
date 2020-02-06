package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
//TODO: rename??
public class MoviePosition {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter
    private Long id;

    @ManyToOne
    @Getter
    private Person person;

    //TODO: check cascade type(cascade = CascadeType.MERGE)
    @ManyToOne
    @Getter
    private Movie movie;

    @NotBlank
    @Getter
    private String position; //=category

    public MoviePosition(Person person, Movie movie, @NotBlank String position) {
        this.person = person;
        this.movie = movie;
        this.position = position;
    }
}
