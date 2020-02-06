package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @OneToOne(mappedBy = "rating")
    @NotNull
    @Getter
    @Setter
    private Movie movie;

    @Max(10)
    @Min(0)
    @Getter
    @NotNull
    private double averageRating;

    @Min(0)
    @Getter
    private long votes;

    public Rating(@NotNull Movie movie, @Max(10) @Min(0) @NotNull double averageRating, @Min(0) long votes) {
        this.movie = movie;
        this.averageRating = averageRating;
        this.votes = votes;
        this.movie.setRating(this);
    }
}
