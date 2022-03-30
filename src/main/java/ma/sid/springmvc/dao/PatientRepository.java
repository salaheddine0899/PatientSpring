package ma.sid.springmvc.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.sid.springmvc.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	Page<Patient> findByNomContains(String mc,Pageable pageable);
}
