package com.gopang.service;

import com.gopang.dto.UserDTO;
import com.gopang.entity.Authority;
import com.gopang.entity.CustumUserDetails;
import com.gopang.repository.UserRepository;
import com.gopang.security.RegistrationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원유저를 생성한 것을 DB에 회원정보 저장
    @Transactional
    public CustumUserDetails join(UserDTO userDTO){
        if (userRepository.findByEmail(userDTO.getEmail()) != null){
            throw new RuntimeException("이미 가입돼 있는 회원입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_UESR")
                .build();

        CustumUserDetails userDetails = RegistrationForm.toUser(passwordEncoder, userDTO,authority);
        return userRepository.save(userDetails);
    }

}
