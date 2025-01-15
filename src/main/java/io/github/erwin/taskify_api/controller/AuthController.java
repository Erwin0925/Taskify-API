package io.github.erwin.taskify_api.controller;

import io.github.erwin.taskify_api.model.Role;
import io.github.erwin.taskify_api.model.UserInfo;
import io.github.erwin.taskify_api.model.UserLogin;
import io.github.erwin.taskify_api.repository.RoleRepository;
import io.github.erwin.taskify_api.repository.UserRepository;
import io.github.erwin.taskify_api.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }

    // ✅ User Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserLogin userLogin){
        // Check if the username already exists
        if (userRepository.findByUserName(userLogin.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Encode the password before saving
        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));

        // Fetch the "USER" role
        Optional<Role> userRole = roleRepository.findByRoleName("USER");
        if (userRole.isEmpty()){
            return ResponseEntity.badRequest().body("Role USER not found!");
        }

        //Assign the role to the user
        Set<Role> roles = new HashSet<>();
        roles.add(userRole.get());
        userLogin.setRoles(roles);

        UserInfo userInfo = new UserInfo();
        userLogin.setUserDetails(userInfo);

        // Save the new user
        userRepository.save(userLogin);

        return ResponseEntity.ok("User Register Successfully!");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin){
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword())
        );

        // Generate a JWT token if authentication is successful
        String token = jwtUtil.generateToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}
