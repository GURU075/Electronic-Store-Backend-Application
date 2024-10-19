package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.UserDto;
import com.guru.electronic.strore.entities.User;
import com.guru.electronic.strore.exceptions.DuplicateEmail;
import com.guru.electronic.strore.exceptions.ResourceNotFoundException;
import com.guru.electronic.strore.helper.Helper;
import com.guru.electronic.strore.repositories.UserRepository;
import com.guru.electronic.strore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//    @Override
//    public UserDto createUser(UserDto userDto) {
//        Random ran = new Random();
//        int userId = ran.nextInt(1000);
//        userDto.setUserid(userId);
//        //  dto -> Entity
//        User user = dtoToEntity(userDto);
//
//        User savedUser = userRepository.save(user);
//
//        //  entity -> dto
//        UserDto newDto = entityToDto(savedUser);
//
//        return newDto;
//    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> existingUser  =userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()){
            throw new DuplicateEmail("User with this email already exist");
        }else {
            Random ran = new Random();
            int userId = ran.nextInt(1000);
            userDto.setUserid(userId);
            //  dto -> Entity
            User user = dtoToEntity(userDto);


            User savedUser = userRepository.save(user);

            //  entity -> dto
            UserDto newDto = entityToDto(savedUser);

            return newDto;
        }
    }



    @Override
    public UserDto updateUser(UserDto userDto, int id) {

        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with given id not found"));
        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        userRepository.save(user);

        UserDto newDto = entityToDto(user);

        return newDto;
    }

    @Override
    public UserDto deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with given id not found"));

         String  fullPath =  imagePath+user.getImageName();

         try {
             Path path = Paths.get(fullPath);
                 Files.delete(path);
         }catch (NoSuchFileException ex){
            logger.info("User image Not Found");
            ex.printStackTrace();
         }catch (IOException e){
             e.printStackTrace();

         }
         userRepository.delete(user);

        return entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user->entityToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

//    @Override
//    public List<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir) {
//        Sort sort =(sortDir.equalsIgnoreCase("desc"))?Sort.by( sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(pageNumber, pageSize ,sort );
//        Page<User> page  = userRepository.findAll(pageable);
//        List<User> users =  page.getContent();
//        List<UserDto> userDtos = users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
//
//        return userDtos;
//    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?Sort.by( sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize ,sort );
        Page<User> page  = userRepository.findAll(pageable);


        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with given id not found"));
        UserDto userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with this email not found"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos =users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());


        return userDtos;
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userid(userDto.getUserid())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName()).build();
//
//        return user;
        return mapper.map(userDto,User.class);
    }

    private UserDto entityToDto(User savedUser) {
//               UserDto userDto = UserDto.builder()
//                        .userid(savedUser.getUserid())
//                        .name(savedUser.getName())
//                        .email(savedUser.getEmail())
//                        .password(savedUser.getPassword())
//                        .about(savedUser.getAbout())
//                        .gender(savedUser.getGender())
//                        .imageName(savedUser.getImageName()).build();
//
//               return userDto;
        return mapper.map(savedUser,UserDto.class);


    }
}
