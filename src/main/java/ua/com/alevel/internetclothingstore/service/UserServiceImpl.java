package ua.com.alevel.internetclothingstore.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.internetclothingstore.dao.UserDao;
import ua.com.alevel.internetclothingstore.dto.UserDTO;
import ua.com.alevel.internetclothingstore.mapper.UserMapper;
import ua.com.alevel.internetclothingstore.model.Role;
import ua.com.alevel.internetclothingstore.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = UserMapper.MAPPER;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userRepository;

    public UserServiceImpl(UserDao userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByNickName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with nickname: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getNickName(),
                user.getPassword(),
                roles
        );
    }

    @Override
    public List<UserDTO> getAll() {
        return mapper.fromUserList(userRepository.findAll());
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole(Role.CLIENT);
        userRepository.save(mapper.toUser(userDTO));
        return true;
    }

    @Override
    public User findFirstByNickName(String name) {
        return userRepository.findFirstByNickName(name);
    }

    @Override
    public void deleteUserById(String id) {
        if (id.isBlank()) {
            throw new RuntimeException("ID path is null or blank!!!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByNickName(String name) {
        return mapper.fromUser(userRepository.findFirstByNickName(name));
    }

    @Override
    @Transactional
    public void updatePassword(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(mapper.toUser(userDTO));
    }
}
