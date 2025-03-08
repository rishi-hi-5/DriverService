package com.reftech.backend.driverbackend.repository;

import com.reftech.backend.driverbackend.model.Driver;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface DriverRepository extends ReactiveCrudRepository<Driver, UUID>{

    @Query("SELECT * FROM drivers LIMIT :size OFFSET :offset")
    Flux<Driver> findAllPaginated(@Param("size") int size,
                                        @Param("offset") int offset);

    @Query("SELECT COUNT(*) FROM drivers")
    Mono<Long> countDrivers();
}
