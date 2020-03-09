package com.maotion.covid19api.batch;

import com.maotion.covid19api.entities.Stats;
import com.maotion.covid19api.entities.TimeSeriesConfirmed;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.batch.item.ItemProcessor;

import java.io.Reader;
import java.util.List;

public class Processor implements ItemProcessor<Reader, List<Stats>> {
    @Override
    public List<Stats> process(Reader reader) throws Exception {
        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                .withType(TimeSeriesConfirmed.class).withSkipLines(1)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<Stats> statsList = csvToBean.parse();
        return statsList;
    }
}
