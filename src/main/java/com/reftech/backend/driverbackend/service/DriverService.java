package com.reftech.backend.driverbackend.service;

import com.reftech.backend.driverbackend.api.DriverRequest;
import com.reftech.backend.driverbackend.api.DriverResponse;
import com.reftech.backend.driverbackend.api.DriverUpdateRequest;
import com.reftech.backend.driverbackend.api.PaginatedDriversResponse;
import com.reftech.backend.driverbackend.mapper.DriverMapper;
import com.reftech.backend.driverbackend.model.Driver;
import com.reftech.backend.driverbackend.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Mono<DriverResponse> updateDriver(UUID driverId, Mono<DriverUpdateRequest> driverUpdateRequest) {
        return driverRepository.findById(driverId)
                .zipWith(driverUpdateRequest)
                .map(tuple -> {
                    Driver driver = tuple.getT1();
                    driver.setName(tuple.getT2().getName());
                    driver.setEmail(tuple.getT2().getEmail());
                    driver.setPhone(tuple.getT2().getPhone());
                    driver.setVehicleId(tuple.getT2().getVehicleId());
                    driver.setLicenseNumber(tuple.getT2().getLicenseNumber());
                    driver.setStatus(tuple.getT2().getStatus().toString());
                    return driver;
                }).flatMap(driverRepository::save)
                .map(driverMapper::toDto);
    }

    public Mono<PaginatedDriversResponse> getAllDrivers(int page, int size, String sortBy, String direction) {
        int offset = page * size; // Calculate offset for pagination

        // we will see what to do with sort and direction params
        return driverRepository.countDrivers()
                .flatMap(totalElements -> driverRepository.findAllPaginated(size, offset)
                        .collectList()
                        .map(drivers -> {
                            List<DriverResponse> response = drivers
                                    .stream()
                                    .map(driverMapper::toDto)
                                    .collect(Collectors.toList());
                            PaginatedDriversResponse paginatedDriversResponse = new PaginatedDriversResponse();
                            paginatedDriversResponse.setContent(response);
                            paginatedDriversResponse.setNumber(totalElements.intValue());
                            paginatedDriversResponse.setSize(size);
                            paginatedDriversResponse.setTotalPages(size/(page+1));
                            return paginatedDriversResponse;
                        })
                );
    }
}
