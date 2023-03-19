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
    //private final CsvWriter csvWriter;
    private final CsvWriter2 csvWriter2;
    //private final CsvWriter3 csvWriter3;
    private final CsvWriter4 csvWriter4;
    private static final int chunkSize = 50000;
    //Job : 하나의 배치 작업 단위
    //하나의 Job안에는 여러 Step이 존재하고 Step안에 Reader,Writer등이 포함된다
    @Bean
    public Job csvFileItemReaderJob() {
        return jobBuilderFactory.get("CsvToDBJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() {
        return stepBuilderFactory.get("CsvToDBStep")
                //CsvEntity를 Reader에서 읽어오고 CsvEntity를 writer에게 넘겨줄 것이다
                .<CsvEntity, CsvEntity>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter4).allowStartIfComplete(true) //배포 시 이거 없애야 함(아니면 서버 돌릴 때 마다 이 작업 반복)
                .build();
    }
}
