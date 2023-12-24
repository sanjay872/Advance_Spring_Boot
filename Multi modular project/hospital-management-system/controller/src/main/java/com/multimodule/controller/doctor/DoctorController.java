package com.multimodule.controller.doctor;

import com.multimodule.model.doctor.Doctor;
import com.multimodule.service.doctor.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class DoctorController {
    @Autowired
    private DoctorService service;

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctor(){
        return service.getAllDoctor();
    }
}
