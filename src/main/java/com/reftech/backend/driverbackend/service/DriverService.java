package com.reftech.backend.driverbackend.service;

import com.reftech.backend.driverbackend.api.DriverRequest;
import com.reftech.backend.driverbackend.api.DriverResponse;
import com.reftech.backend.driverbackend.api.DriverUpdateRequest;
import com.reftech.backend.driverbackend.mapper.DriverMapper;
import com.reftech.backend.driverbackend.model.Driver;
import com.reftech.backend.driverbackend.repository.DriverRepository;
import io.netty.util.AsyncMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public Mono<DriverResponse> registerDriver(Mono<DriverRequest> driverRequest) {
        return driverRequest
                .flatMap(request -> driverRepository.save(driverMapper.toEntity(request)))
                .map(driverMapper::toDto);
    }

    public Mono<Void> deleteDriver(UUID driverId) {
        return driverRepository.deleteById(driverId);
    }

    public Mono<DriverResponse> getDriver(UUID driverId) {
        return driverRepository.findById(driverId)
                .map(driverMapper::toDto);
    }

    public Mono<Void> updateDriver(UUID driverId, Mono<DriverUpdateRequest> driverUpdateRequest) {
        return driverRepository.findById(driverId)
                .zipWith(driverUpdateRequest)
                .map(tuple -> {
                    Driver driver = tuple.getT1();
                    driver.setName(tuple.getT2().getName());
                    driver.setPhone(tuple.getT2().getPhone());
                    driver.setVehicleId(tuple.getT2().getVehicleId());
                    driver.setLicenseNumber(tuple.getT2().getLicenseNumber());
                    driver.setStatus(tuple.getT2().getStatus().toString());
                    return driver;
                }).flatMap(driverRepository::save)
                .then();
    }
}
