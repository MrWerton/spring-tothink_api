package com.notrew.tothink.modules.think.repositories;

import com.notrew.tothink.modules.think.entities.Think;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThinkRepository extends JpaRepository<Think, UUID> {
}
