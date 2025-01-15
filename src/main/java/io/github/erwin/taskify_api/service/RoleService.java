package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.Role;
import org.springframework.security.core.Authentication;

public interface RoleService {
    void addRole(Role role);

    void removeRole(Role role);

    void updateUserRole(String username, Role newRole);
}
