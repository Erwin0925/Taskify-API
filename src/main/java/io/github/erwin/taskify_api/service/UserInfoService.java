package io.github.erwin.taskify_api.service;

import io.github.erwin.taskify_api.model.UserInfo;
import io.github.erwin.taskify_api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


public interface UserInfoService {

    void updateUserInfo(UserInfo userInfo, Authentication authentication);

    void deleteUser(String userName);
}
