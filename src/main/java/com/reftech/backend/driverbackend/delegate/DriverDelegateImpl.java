package com.reftech.backend.driverbackend.delegate;

import com.reftech.backend.driverbackend.api.*;
import com.reftech.backend.driverbackend.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DriverDelegateImpl implements DriversApiDelegate {

    private final DriverService driverService;

    public Mono<ResponseEntity<DriverResponse>> driversPost(Mono<DriverRequest> driverRequest,
                                                                ServerWebExchange exchange) {
        return driverService.registerDriver(driverRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<Void>> driversDriverIdDelete(String driverId,
                                                             ServerWebExchange exchange) {
        return driverService.deleteDriver(UUID.fromString(driverId))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    public Mono<ResponseEntity<DriverResponse>> driversDriverIdGet(UUID driverId,
                                                                    ServerWebExchange exchange) {
        return driverService.getDriver(driverId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    public Mono<ResponseEntity<DriverResponse>> driversDriverIdPut(UUID driverId,
                                                          Mono<DriverUpdateRequest> driverUpdateRequest,
                                                          ServerWebExchange exchange) {
        return driverService.updateDriver(driverId, driverUpdateRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<PaginatedDriversResponse>> driversGet(Integer page,
                                                                      Integer size,
                                                                      String sort,
                                                                      String direction,
                                                                      ServerWebExchange exchange) {
        return driverService.getAllDrivers(page,size,sort,direction)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
