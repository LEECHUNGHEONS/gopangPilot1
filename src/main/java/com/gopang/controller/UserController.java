package com.gopang.controller;

import com.gopang.dto.UserDTO;
import com.gopang.entity.CustumUserDetails;
import com.gopang.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<CustumUserDetails> joinup(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.join(userDTO));
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<CustumUserDetails> getMyUserInfo(){
//        return ResponseEntity.ok(userService.)
//    }
}
