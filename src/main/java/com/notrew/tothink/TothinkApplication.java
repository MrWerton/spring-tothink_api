package com.notrew.tothink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class TothinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TothinkApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok().body("hello");
    };

}
