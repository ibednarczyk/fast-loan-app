package com.loan_application.mappers;

import com.loan_application.domain.user.User;
import com.loan_application.representation.UserDto;

public class UserMapper {

    public UserMapper() {
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .active(userDto.isActive())
                .build();
    }

}
