package com.demo.imdb.service.relationships;

import java.util.List;

//TODO: can be parameterized
public interface RelationshipsLoader {
    List<String> loadChildren(String parent);
}
