package com.demo.imdb.model;

import lombok.Getter;

public class TypecastResult {
    @Getter private final String genre;
    @Getter private final Long cnt;

    public TypecastResult(String genre, Long cnt) {
        this.genre = genre;
        this.cnt = cnt;
    }
}