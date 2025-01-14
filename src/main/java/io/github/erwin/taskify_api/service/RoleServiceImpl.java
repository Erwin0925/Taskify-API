package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.Role;
import io.github.erwin.taskify_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (roleRepository.existsByRoleName(role.getRoleName())){
            throw new IllegalArgumentException("Role already exists");
        }
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save role", e);
        }
    }

    @Override
    public void removeRole(Role role) {
        Role existingrole = roleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(()-> new IllegalArgumentException("Role does not exist"));
        roleRepository.delete(existingrole);
    }
}
