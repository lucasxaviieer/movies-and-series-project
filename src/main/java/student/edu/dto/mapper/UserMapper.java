package student.edu.dto.mapper;

import org.modelmapper.ModelMapper;
import student.edu.domain.model.User;
import student.edu.dto.UserDto;

public class UserMapper extends ModelMapper {

    public static UserDto toDto(User user) {
        return new ModelMapper().map(user, UserDto.class);
    }

    public static User toUser(UserDto userDto) {
        return new ModelMapper().map(userDto, User.class);
    }
}
