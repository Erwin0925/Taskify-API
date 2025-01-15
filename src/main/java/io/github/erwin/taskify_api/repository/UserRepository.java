package io.github.erwin.taskify_api.repository;

import io.github.erwin.taskify_api.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserLogin,Integer> {


    Optional<UserLogin> findByUserName(String userName);
}
