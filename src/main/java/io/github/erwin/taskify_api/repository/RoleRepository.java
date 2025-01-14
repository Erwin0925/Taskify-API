package io.github.erwin.taskify_api.repository;

import io.github.erwin.taskify_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);
}
