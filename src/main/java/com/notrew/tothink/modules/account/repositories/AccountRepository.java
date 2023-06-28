package com.notrew.tothink.modules.account.repositories;

import com.notrew.tothink.modules.account.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
