package com.demo.imdb.service.relationships;

import java.util.List;

public interface RelationshipsLoader {
    List<String> loadChildren(String parent);
}
