package com.example.dex_gui.repository;

import com.example.dex_gui.entity.Quartet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuartetRepository extends JpaRepository<Quartet, Long> {

    boolean existsByCompoundAndStudyAndDepartmentAndPartner(
            String compound,
            String study,
            String department,
            String partner
    );

    Optional<Quartet> findByCompoundAndStudyAndDepartmentAndPartner(
            String compound,
            String study,
            String department,
            String partner
    );
}
