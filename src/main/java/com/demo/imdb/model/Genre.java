package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @NotBlank
    @Getter @Setter
    private String name;

    public Genre(@NotBlank String name) {
        this.name = name.toLowerCase();
    }
}
