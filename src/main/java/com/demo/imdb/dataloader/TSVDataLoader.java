package com.demo.imdb.dataloader;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class TSVDataLoader<T> {
    private TsvParser parser;
    private static final Logger logger = LogManager.getLogger(TSVDataLoader.class);

    private final ModelParser<T> modelParser;
    private final String filePath;
    private final RepositoryLoader<T> loader;


    public TSVDataLoader(String filePath, ModelParser<T> modelParser, RepositoryLoader<T> loader) {
        this.filePath = filePath;
        this.modelParser = modelParser;
        this.loader = loader;
        setParser();
    }

    private void setParser() {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderExtractionEnabled(true);
        parser = new TsvParser(settings);
    }

    public void loadItems() throws IOException {
        long cnt = 0;
        for (Record record : parser.iterateRecords(getReader())) {
                try {
                    loader.save(modelParser.parse(record));
                    cnt++;
                    if (cnt % 1000 == 0)
                        System.out.println("Processed " + cnt);
                } catch (Exception e) {
                    logger.error("Couldn't save record " + record.toString());
                }
        }
    }


    private Reader getReader() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(filePath);
        if (stream == null)
            throw new FileNotFoundException();
        return new InputStreamReader(stream, "UTF-8");
    }
}
