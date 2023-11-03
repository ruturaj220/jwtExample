package com.ruturaj.jwtexample.controller;

import com.ruturaj.jwtexample.entity.Patient;
import com.ruturaj.jwtexample.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        return this.patientService.createPatient(patient);
    }

    @GetMapping("/getAllPatient")
    public ResponseEntity<List<Patient>> getAllPatient(){
        return this.patientService.getAllPatient();
    }

    @GetMapping("/getPatient/{id}")
    public ResponseEntity<Optional<Patient>> getPatient(@PathVariable Long id){
        return this.patientService.getPatient(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){
        return this.patientService.deletePatient(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient, @PathVariable Long id) throws Exception {
        return this.patientService.updatePatient(patient,id);
    }
}
