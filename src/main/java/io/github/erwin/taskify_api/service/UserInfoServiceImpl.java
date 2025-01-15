package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.UserInfo;
import io.github.erwin.taskify_api.model.UserLogin;
import io.github.erwin.taskify_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    private final UserRepository userRepository;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, Authentication authentication) {
        if (userInfo == null){
            throw new IllegalArgumentException("Username and UserInfo cannot be NULL");
        }

        String username = authentication.getName();

        UserLogin userLogin = userRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("UserName not Found!!"));

        UserInfo olduserInfo = userLogin.getUserDetails();
        olduserInfo.setFullName(userInfo.getFullName());
        olduserInfo.setDob(userInfo.getDob());
        olduserInfo.setLocation(userInfo.getLocation());

        userRepository.save(userLogin);
    }

    @Override
    public void deleteUser(String userName) {
        UserLogin userLogin = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("UserName not Found!!"));
        userRepository.delete(userLogin);
    }
}
