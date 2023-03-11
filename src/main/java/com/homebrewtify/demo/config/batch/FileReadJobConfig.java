package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.CsvEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class FileReadJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private static final int chunkSize = 1;
    //Job : 하나의 배치 작업 단위
    //하나의 Job안에는 여러 Step이 존재하고 Step안에 Reader,Writer등이 포함된다
    @Bean
    public Job csvFileItemReaderJob() {
        return jobBuilderFactory.get("DataFileItemReaderJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() {
        return stepBuilderFactory.get("DataFileItemReaderStep")
                //CsvEntity를 Reader에서 읽어오고 CsvEntity를 writer에게 넘겨줄 것이다
                .<CsvEntity, CsvEntity>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter)
                .build();
    }
}
