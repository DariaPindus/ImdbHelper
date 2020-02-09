package com.demo.imdb.dataloader;

public interface RepositoryLoader<T> {
    T save(T item);
}
