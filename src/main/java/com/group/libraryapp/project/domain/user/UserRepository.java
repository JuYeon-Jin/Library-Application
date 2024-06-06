package com.group.libraryapp.project.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Optional<User> findByName(String name);
    Optional<User> findByIdAndPw(String id, String pw);
    Optional<User> findByPrivateId(String privateId);
}
