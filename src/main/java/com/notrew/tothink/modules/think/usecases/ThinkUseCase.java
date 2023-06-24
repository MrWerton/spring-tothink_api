package com.notrew.tothink.modules.think.usecases;

import com.notrew.tothink.modules.think.entities.Think;
import com.notrew.tothink.modules.think.repositories.ThinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThinkUseCase {
    private final ThinkRepository thinkRepository;

    @Autowired
    public ThinkUseCase(ThinkRepository thinkRepository) {
        this.thinkRepository = thinkRepository;
    }


    public List<Think> getThink() {
        return thinkRepository.findAll();
    }

    public Think saveThink(Think think) {
        return thinkRepository.save(think);
    }
}
