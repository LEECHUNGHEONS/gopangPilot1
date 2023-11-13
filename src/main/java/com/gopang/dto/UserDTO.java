package com.gopang.dto;

import com.gopang.controller.request.UserRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Data
@RequiredArgsConstructor
//@Getter
//@Setter
public class UserDTO {

//    private final Long id;
    @NotNull
    @Size(min = 5, max = 25)
    private final String email;
    @NotNull
    @Size(min =5 , max = 18)
    private final String password;

    public static UserDTO translate(UserRequest request){
        return new UserDTO( request.getEmail(), request.getEmail());
    }
}
