package com.carrent.controller;

import com.carrent.domain.User;
import com.carrent.dto.CreateUserDto;
import com.carrent.dto.UserDto;
import com.carrent.dto.UserDtoList;
import com.carrent.exception.UserNotFoundException;
import com.carrent.mapper.UserMapper;
import com.carrent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<UserDtoList> getUsers(){
        return userMapper.getUsersDtoList(userService.getUsersList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public UserDto getUserDto (@PathVariable Long userId) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.getUser(userId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public User createUser(@RequestBody final CreateUserDto createUserDto) {
        return userService.saveUser(userMapper.mapToUser(createUserDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}