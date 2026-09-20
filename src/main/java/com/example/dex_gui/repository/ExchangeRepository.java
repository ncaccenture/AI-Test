package com.example.dex_gui.repository;

import com.example.dex_gui.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {
    Optional<Exchange> findBySourceQuartet_CompoundAndSourceQuartet_StudyAndSourceQuartet_DepartmentAndSourceQuartet_PartnerAndSourceEndpointAndDestinationEndpoint(
            String compound,
            String study,
            String department,
            String partner,
            String sourceEndpoint,
            String destinationEndpoint
    );
}