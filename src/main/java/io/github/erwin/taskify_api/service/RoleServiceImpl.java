package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.Role;
import io.github.erwin.taskify_api.model.UserLogin;
import io.github.erwin.taskify_api.repository.RoleRepository;
import io.github.erwin.taskify_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

    @Override
    // @Transactional
    public void updateUserRole(String username, Role newRole) {
        UserLogin userLogin = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));

        Role role = roleRepository.findByRoleName(newRole.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role Not Found!"));

        userLogin.getRoles().add(role);
        userRepository.save(userLogin);
    }
}

