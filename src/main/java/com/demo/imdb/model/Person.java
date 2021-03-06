package com.demo.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(indexes = {@Index(name = "person_id_index",  columnList="id", unique = true),
        @Index(name = "person_name_index", columnList="name", unique = false)})
@NoArgsConstructor
public class Person {
    @Id
    @Getter
    private String id;

    @NotBlank
    @Getter @Setter
    @Column(length = 350)
    private String name;

    @OneToMany(mappedBy = "person")
    @Getter
    private Set<MoviePosition> movies;

    public Person(String id) {
        this.id = id;
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }
}
