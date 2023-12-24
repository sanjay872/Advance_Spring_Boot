package com.multimodule.controller.patient;

import com.multimodule.model.patient.Patient;
import com.multimodule.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class PatientController {
    @Autowired
    private PatientService service;

    @GetMapping("/patients")
    public List<Patient> getAllPatient(){
        return service.getAllPatient();
    }
}
