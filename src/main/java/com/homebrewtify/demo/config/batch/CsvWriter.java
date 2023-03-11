package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.CsvEntity;
import com.homebrewtify.demo.repository.CsvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<CsvEntity> {

    private final CsvRepository csvRepository;

    @Override
    public void write(List<? extends CsvEntity> list) throws Exception {
        System.out.println("Called Writer");
        csvRepository.saveAll(new ArrayList<CsvEntity>(list));

    }
}