package ua.com.alevel.internetclothingstore.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.internetclothingstore.dto.UserDTO;
import ua.com.alevel.internetclothingstore.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO dto);

    @InheritInverseConfiguration
    UserDTO fromUser(User user);

    List<User> toUserList(List<UserDTO> dtos);

    List<UserDTO> fromUserList(List<User> users);

}