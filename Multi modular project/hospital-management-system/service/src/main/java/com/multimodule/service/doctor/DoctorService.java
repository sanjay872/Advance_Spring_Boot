package com.multimodule.service.doctor;

import com.multimodule.dao.doctor.DoctorRepository;
import com.multimodule.email.EmailService;
import com.multimodule.model.doctor.Doctor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private EmailService service;

    @PostConstruct
    public void initDoctor(){
        repository.saveAll(Stream.of(new Doctor(101L,"Chopper","All"),
                new Doctor(102L,"Law","All")).collect(Collectors.toList()));
    }

    public List<Doctor> getAllDoctor(){
        service.sentEmail();
        return  repository.findAll();
    }
}
