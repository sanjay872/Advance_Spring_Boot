package com.multimodule.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sentEmail(){
        System.out.println("Email Service working");
    }
}
