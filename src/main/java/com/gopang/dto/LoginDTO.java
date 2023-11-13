package com.gopang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull
    @Size(min = 5, max = 25)
    private String email;

    @JsonProperty
    @NotNull
    @Size(min =5 , max = 18)
    private String password;
}
