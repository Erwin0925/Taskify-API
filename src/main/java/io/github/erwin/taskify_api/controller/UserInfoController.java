package io.github.erwin.taskify_api.controller;

import io.github.erwin.taskify_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserInfoController {

    private UserRepository userRepository;

    @Autowired
    public UserInfoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
