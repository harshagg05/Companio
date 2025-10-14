package com.companio.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.companio.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip,UUID>,JpaSpecificationExecutor<Trip>{
    List<Trip> findByUserIdAndSlugStartingWithIgnoreCase(UUID userId,String slugPrefix);
}
