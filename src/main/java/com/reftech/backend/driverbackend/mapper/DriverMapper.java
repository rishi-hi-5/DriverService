package com.reftech.backend.driverbackend.mapper;

import com.reftech.backend.driverbackend.api.DriverRequest;
import com.reftech.backend.driverbackend.api.DriverResponse;
import com.reftech.backend.driverbackend.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponse toDto(Driver driver);
    Driver toEntity(DriverRequest driver);
}
