package com.loan_application.mappers;

import com.loan_application.domain.user.User;
import com.loan_application.representation.UserDto;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class UserMapper {

    public UserMapper() {
    }

    public static UserDto mapToUserDto(User user) {
        MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();
        return mapper.map(user, UserDto.class);
    }

    public static User mapToUser(UserDto userDto) {
        MapperFacade mapperFacade = new DefaultMapperFactory.Builder().build().getMapperFacade();
        return mapperFacade.map(userDto, User.class);
    }

}
