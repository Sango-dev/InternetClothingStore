package ua.com.alevel.internetclothingstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.internetclothingstore.model.User;

public interface UserDao extends JpaRepository<User, String> {
    User findFirstByNickName(String name);
}
