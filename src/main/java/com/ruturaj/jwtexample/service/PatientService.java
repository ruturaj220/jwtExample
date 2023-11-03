package com.ruturaj.jwtexample.service;

import com.ruturaj.jwtexample.entity.Patient;
import com.ruturaj.jwtexample.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public ResponseEntity<Patient> createPatient(Patient patient) {
        try {
            Patient createdPatient = this.patientRepository.save(patient);
            return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Patient>> getAllPatient() {
        try {
            List<Patient> patients = this.patientRepository.findAll();
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Optional<Patient>> getPatient(Long id) {
        try {
            Optional<Patient> patient = this.patientRepository.findById(id);
            return new ResponseEntity<>(patient, patient.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            System.out.println("Patient not found");
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } else {
            this.patientRepository.deleteById(id);
            return new ResponseEntity<>("Patient deleted", HttpStatus.OK);
        }
    }

    public ResponseEntity<Patient> updatePatient(Patient newPatient, Long id) throws Exception{
        try {
            Patient updatedPatient = this.patientRepository.findById(id)
                    .map(patient -> {
                        patient.setName(newPatient.getName());
                        patient.setContact_details(newPatient.getContact_details());
                        patient.setAddress(newPatient.getAddress());
                        patient.setPincode(newPatient.getPincode());
                        return this.patientRepository.save(patient);
                    })
                    .orElseThrow(() -> new UsernameNotFoundException("Patient is not found with ID: " + id));

            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
