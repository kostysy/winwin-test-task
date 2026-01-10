package org.example.authapi.repositories;

import org.example.authapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);

    boolean existsUserByEmail(String email);
}
