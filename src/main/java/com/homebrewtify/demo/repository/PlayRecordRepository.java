package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.PlayRecord;
import com.homebrewtify.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayRecordRepository extends JpaRepository<PlayRecord,String> {
    List<PlayRecord> findAllByUser(User user);

    List<PlayRecord> findAllByUserOrderByPlayDate(User user);
}
