package com.modules.service.controller;

import com.modules.service.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService service;

    @GetMapping
    public ResponseEntity getAllPlayer(){
        return new ResponseEntity(service.getAllPlayer(), HttpStatus.OK);
    }
}
