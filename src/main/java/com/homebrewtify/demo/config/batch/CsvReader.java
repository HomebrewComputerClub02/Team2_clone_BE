package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.CsvEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class CsvReader {
    @Bean
    public FlatFileItemReader<CsvEntity> csvFileItemReader() {
        FlatFileItemReader<CsvEntity> flatFileItemReader = new FlatFileItemReader<>();
        //resources/csv/dataset.csv 를 resource경로로 지정
        flatFileItemReader.setResource(new ClassPathResource("/csv/dataset.csv"));
        //맨 윗줄은 column 이름이니 skip
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8");


        //각 줄을 CsvEntity로 매핑한
        DefaultLineMapper<CsvEntity> defaultLineMapper = new DefaultLineMapper<>();
        // 각 Column을 ,로 구분
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames("id","trackId", "artists","albumName","trackName","popularity","duration","explicit","dance","energy","keyValue","loudness","mode","speech","acoustic","instrument","live","valance","tempo","timeSignature","trackGenre");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<CsvEntity> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(CsvEntity.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }

}
