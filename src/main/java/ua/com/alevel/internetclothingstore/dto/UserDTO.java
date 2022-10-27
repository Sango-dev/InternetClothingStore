package ua.com.alevel.internetclothingstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.alevel.internetclothingstore.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String nickName;
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
    private Role role;
}