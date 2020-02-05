package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
public class Rating {
    @Id
    @Getter @Setter
    private Long id;

    @OneToOne
    @NotNull
    private Movie movie;
}
