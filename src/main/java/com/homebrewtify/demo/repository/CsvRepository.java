package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.CsvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvRepository extends JpaRepository<CsvEntity, Long> {
}
