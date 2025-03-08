package com.reftech.backend.driverbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("drivers")
@Data
@AllArgsConstructor
public class Driver implements Persistable<UUID> {

    @Id
    UUID id;
    String name;
    String phone;

    String email;

    @Column("vehicleId")
    String vehicleId;

    @Column("licenseNumber")

    String licenseNumber;
    String status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Driver() { //CHECK: should not be required
        this.id = UUID.randomUUID(); // Generate ID before saving
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}