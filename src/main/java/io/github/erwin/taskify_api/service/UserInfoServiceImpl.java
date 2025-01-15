package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.UserInfo;
import io.github.erwin.taskify_api.model.UserLogin;
import io.github.erwin.taskify_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserInfoServiceImpl implements UserInfoService{

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, Authentication authentication) {
        long startTime = System.currentTimeMillis();

        logger.info("Starting updateUserInfo for user : {}", authentication.getName());

        if (userInfo == null){
            logger.error("UserInfo is null for user : {}", authentication.getName());
            throw new IllegalArgumentException("Username and UserInfo cannot be NULL");
        }

        String username = authentication.getName();

        UserLogin userLogin = userRepository.findByUserName(username)
                .orElseThrow(()-> {
                    logger.error("User not found : {}", username);
                    return new UsernameNotFoundException("UserName not Found!!");
                });


        UserInfo olduserInfo = userLogin.getUserDetails();
        olduserInfo.setFullName(userInfo.getFullName());
        olduserInfo.setDob(userInfo.getDob());
        olduserInfo.setLocation(userInfo.getLocation());

        userRepository.save(userLogin);
        logger.info("user info updated successfully for user : {}", username);

        long endTime = System.currentTimeMillis();
        logger.info("Execution time for updateUserInfo : {} ms", (endTime-startTime));
    }

    @Override
    public void deleteUser(String userName) {
        logger.info("Starting deleteUser for username: {}", userName);

        UserLogin userLogin = userRepository.findByUserName(userName)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", userName);
                    return new UsernameNotFoundException("UserName not Found!!");
                });

        userRepository.delete(userLogin);
        logger.info("User deleted successfully: {}", userName);
    }
}
