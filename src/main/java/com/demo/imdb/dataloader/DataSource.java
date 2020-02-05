package com.demo.imdb.dataloader;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DataSource<T> {
    List<T> getItems() throws Exception;
}
