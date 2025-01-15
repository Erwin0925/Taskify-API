package io.github.erwin.taskify_api.controller;

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

    private UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> UpdateUserInfo (@RequestBody UserInfo userInfo, Authentication authentication){
        try{
            userInfoService.updateUserInfo(userInfo, authentication);
            return ResponseEntity.ok("User info updated Successfully!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update UserDetails");
        }
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String userName){
        try {
            userInfoService.deleteUser(userName);
            return ResponseEntity.ok(userName + " got deleted successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete "+ userName);
        }
    }
}
