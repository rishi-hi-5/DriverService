package com.reftech.backend.driverbackend.repository;

import com.reftech.backend.driverbackend.model.Driver;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverRepository extends ReactiveCrudRepository<Driver, UUID>{
}
