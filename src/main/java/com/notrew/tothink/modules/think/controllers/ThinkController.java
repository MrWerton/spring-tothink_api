package com.notrew.tothink.modules.think.controllers;


import com.notrew.tothink.modules.think.dtos.ThinkDto;
import com.notrew.tothink.modules.think.entities.Think;
import com.notrew.tothink.modules.think.usecases.ThinkUseCase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/think")
public class ThinkController {
    private final ThinkUseCase thinkUseCase;

    @Autowired
    public ThinkController(ThinkUseCase thinkUseCase) {
        this.thinkUseCase = thinkUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<List<Think>> getAllThink() {
        var thinks = thinkUseCase.getThink();

        return ResponseEntity.ok(thinks);
    }

    @PostMapping("/")
    public ResponseEntity<Think> saveThink(@RequestBody ThinkDto thinkDto) {
        var think = new Think();
        BeanUtils.copyProperties(thinkDto, think);
        var response = thinkUseCase.saveThink(think);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
