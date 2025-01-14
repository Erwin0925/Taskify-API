package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.Role;

public interface RoleService {
    void addRole(Role role);

    void removeRole(Role role);
}
