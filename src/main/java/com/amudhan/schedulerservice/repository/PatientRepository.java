package com.amudhan.schedulerservice.repository;

import com.amudhan.schedulerservice.domain.Patient;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
public interface PatientRepository extends JpaRepository<Patient,Long> {

}
