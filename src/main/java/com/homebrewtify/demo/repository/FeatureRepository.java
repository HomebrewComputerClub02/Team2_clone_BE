package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.MusicFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<MusicFeature,String> {

}
