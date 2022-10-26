package com.security.demo.app.repository;

import com.security.demo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name = :name ")
    Optional<User> findByName(@Param("name") String name);

    @Query("select u from User u")
    void someQuery();
}
