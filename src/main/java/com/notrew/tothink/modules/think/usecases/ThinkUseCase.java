package com.notrew.tothink.modules.think.usecases;

import com.notrew.tothink.modules.think.entities.Think;
import com.notrew.tothink.modules.think.repositories.ThinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Think updateThink(UUID id, Think think) throws Exception {
        Optional<Think> optionalThink = thinkRepository.findById(id);

        if (optionalThink.isPresent()) {
            Think currentThink = optionalThink.get();
            currentThink.setBody(think.getBody());
            currentThink.setTitle(think.getTitle());

            return thinkRepository.save(currentThink);
        } else {
            throw new NotFoundException();
        }
    }


}
