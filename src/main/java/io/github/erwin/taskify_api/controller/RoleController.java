package io.github.erwin.taskify_api.controller;

import io.github.erwin.taskify_api.model.Role;
import io.github.erwin.taskify_api.model.UserLogin;
import io.github.erwin.taskify_api.repository.RoleRepository;
import io.github.erwin.taskify_api.repository.UserRepository;
import io.github.erwin.taskify_api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        try {
            roleService.addRole(role);
            return ResponseEntity.ok("Role added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add role");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRole(@RequestBody Role role){
        try{
            roleService.removeRole(role);
            return ResponseEntity.ok("Role is remove successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove role");
        }

    }

    @PutMapping("/update-role/{username}")
    public ResponseEntity<String> updateUserRole(@PathVariable String username, @RequestBody Role newRole) {
        try {
            roleService.updateUserRole(username, newRole);
            return ResponseEntity.ok("User role updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user role");
        }
    }

}
