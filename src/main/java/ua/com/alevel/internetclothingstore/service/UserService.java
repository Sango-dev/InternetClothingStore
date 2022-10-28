package ua.com.alevel.internetclothingstore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.com.alevel.internetclothingstore.dto.UserDTO;
import ua.com.alevel.internetclothingstore.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findFirstByNickName(String name);

    boolean save(UserDTO userDTO);

    void save(User user);

    List<UserDTO> getAll();

    void deleteUserById(String id);

    UserDTO getByNickName(String name);

    void updatePassword(UserDTO userDTO);
}
