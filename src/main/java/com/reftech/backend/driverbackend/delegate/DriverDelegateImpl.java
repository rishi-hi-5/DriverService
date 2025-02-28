package com.reftech.backend.driverbackend.delegate;

import com.reftech.backend.driverbackend.api.DriverRequest;
import com.reftech.backend.driverbackend.api.DriverResponse;
import com.reftech.backend.driverbackend.api.DriverUpdateRequest;
import com.reftech.backend.driverbackend.api.DriversApiDelegate;
import com.reftech.backend.driverbackend.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class DriverDelegateImpl implements DriversApiDelegate {

    private final DriverService driverService;

    public Mono<ResponseEntity<DriverResponse>> apiDriversPost(Mono<DriverRequest> driverRequest,
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

    public Mono<ResponseEntity<Void>> driversDriverIdPut(UUID driverId,
                                                          Mono<DriverUpdateRequest> driverUpdateRequest,
                                                          ServerWebExchange exchange) {
        return driverService.updateDriver(driverId, driverUpdateRequest)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
