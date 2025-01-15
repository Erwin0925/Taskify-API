package io.github.erwin.taskify_api.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.github.erwin.taskify_api.model.UserInfo;
import io.github.erwin.taskify_api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    private UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> UpdateUserInfo (@RequestBody UserInfo userInfo, Authentication authentication){
        logger.info("Received request to update user info for user : {}", authentication.getName());

        try{
            userInfoService.updateUserInfo(userInfo, authentication);
            logger.info("User info for '{}' updated successfully.", authentication.getName());
            return ResponseEntity.ok("User info updated Successfully!");
        }catch (Exception e){
            logger.error("Error updating user info for '{}' : ", authentication.getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update UserDetails");
        }
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String userName){
        logger.info("Received request to delete user : '{}'", userName);

        try {
            userInfoService.deleteUser(userName);
            logger.info("User '{}' deleted successfully", userName);
            return ResponseEntity.ok(userName + " got deleted successfully");
        }catch (Exception e){
            logger.error("Error deleting user '{}' : {}",userName, e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete "+ userName);
        }
    }
}
