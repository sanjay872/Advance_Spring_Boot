package com.multimodule.service.patient;

import com.multimodule.dao.patient.PatientRepository;
import com.multimodule.email.EmailService;
import com.multimodule.model.patient.Patient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PatientService {
    @Autowired
    private PatientRepository repository;

    @Autowired
    private EmailService service;

    @PostConstruct
    public void initPatient(){
        repository.saveAll(Stream.of(new Patient(201L,"Kaido"),
                new Patient(202L,"Big Mom")).collect(Collectors.toList()));
    }

    public List<Patient> getAllPatient(){
        service.sentEmail();
        return repository.findAll();
    }
}
