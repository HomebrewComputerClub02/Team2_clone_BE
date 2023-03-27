package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SingerRepository extends JpaRepository<Singer,String> {
    @EntityGraph(attributePaths = "albums")
    Optional<Singer> findWithAlbumsById(String singeId);
}
