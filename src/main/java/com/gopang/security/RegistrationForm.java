package com.gopang.security;

import com.gopang.dto.UserDTO;
import com.gopang.entity.Authority;
import com.gopang.entity.CustumUserDetails;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private static BCryptPasswordEncoder bCryptPasswordEncoder;

    public static CustumUserDetails toUser(BCryptPasswordEncoder bCryptPasswordEncoder, UserDTO userDTO, Authority authority){
        return new CustumUserDetails(null, userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()),authority);
    }
}
