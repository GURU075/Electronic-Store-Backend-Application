package com.guru.electronic.strore.dtos;

import com.guru.electronic.strore.validate.ImageNameValidate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private int userid;

    @Size(min = 3, max = 30, message = "Invalid Naem!!")
    private String name;

//    @Email(message = "Invalid Email!!")    @Email is not validating proper
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Invalid Email!!")
    @NotBlank(message = "email must not ne blank!!")
    private String email;

    @NotBlank(message = "Password is required!!")
    private String password;

    private String gender;

    @NotBlank(message = "About must not be blank")
    private String about;


    @ImageNameValidate(message = "Image is not valid")
    private String imageName;
}
