package com.demo.imdb.dataloader;

import com.univocity.parsers.common.record.Record;

public interface ModelParser<T> {
    T parse(Record record);
}
