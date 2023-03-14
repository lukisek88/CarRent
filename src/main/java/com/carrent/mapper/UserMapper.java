package com.carrent.mapper;

import com.carrent.domain.User;
import com.carrent.dto.CreateUserDto;
import com.carrent.dto.UserDto;
import com.carrent.dto.UserDtoList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(final CreateUserDto createUserDto) {
        return new User(
                createUserDto.getName(),
                createUserDto.getSurname(),
                createUserDto.getPhone(),
                createUserDto.getEamil(),
                createUserDto.getPassword()
        );
    }

    public List<UserDtoList> getUsersDtoList(final List<User> userList) {
        return userList.stream()
                .map(u -> new UserDtoList(
                        u.getId(),
                        u.getName(),
                        u.getSurname(),
                        u.getPhone(),
                        u.getEmail(),
                        u.isLoginStatus()))
                .collect(Collectors.toList());
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPhone(),
                user.getEmail(),
                user.isLoginStatus(),
                user.getOrderList().stream()
                        .map(o -> o.getId())
                        .collect(Collectors.toList()),
                user.getInvoiceList().stream()
                        .map(i -> i.getId())
                        .collect(Collectors.toList()));
    }
}