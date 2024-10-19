package com.guru.electronic.strore.services;

import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.UserDto;
import com.guru.electronic.strore.entities.User;

import java.util.List;

public interface UserService {

    //avoid using same class to transfer in parameter and represent at same type like
    // User createUser(User user);   for this use DTO
     UserDto createUser(UserDto userDto);

     UserDto updateUser(UserDto userDto,int id);

     UserDto deleteUser(int id);

     List<UserDto> getAllUsers();

//     List<UserDto> getAllUsers(int pageNumber,int pageSize , String sortBy , String sortDirection);

      PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize , String sortBy , String sortDirection);

     UserDto getUserById(int id);

     UserDto getUserByEmail(String email);

     List<UserDto> searchUser(String keyword);
}
