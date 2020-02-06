package com.demo.imdb.dataloader;

import com.demo.imdb.repository.MovieRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class TSVDataSource<T> implements DataSource<T> {

    private TsvParser parser;

    private final ModelParser<T> modelParser;
    private final String filePath;


    public TSVDataSource(String filePath, ModelParser<T> modelParser) {
        this.filePath = filePath;
        this.modelParser = modelParser;
        setParser();
    }

    private void setParser() {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderExtractionEnabled(true);
        parser = new TsvParser(settings);
    }

    public List<T> getItems() throws IOException {
        List<T> result = new ArrayList<>();

        for(Record record : parser.iterateRecords(getReader())){
            result.add(modelParser.parse(record));
        }

        return result;
    }


    private Reader getReader() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(filePath);
        if (stream == null)
            throw new FileNotFoundException();
        return new InputStreamReader(stream, "UTF-8");
    }
}
