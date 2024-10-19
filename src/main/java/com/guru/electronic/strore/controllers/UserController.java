package com.guru.electronic.strore.controllers;

import com.guru.electronic.strore.dtos.ApiResponseMessage;
import com.guru.electronic.strore.dtos.ImageResponse;
import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.UserDto;
import com.guru.electronic.strore.services.FileService;
import com.guru.electronic.strore.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto user =  userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") int userId){
        UserDto user = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted successfully!!")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUser(){
//        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
//
//    }

//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUser(@RequestParam(value = "pageNumber" ,defaultValue = "0", required = false) int pageNumber,
//                                                    @RequestParam(value="pageSize", defaultValue="10",required=false)int pageSize,
//                                                    @RequestParam(value="sortBy", defaultValue ="name", required = false)String sortBy,
//                                                    @RequestParam(value = "sortDirection", defaultValue = "ASC",required = false)String sortDirection){
//
//
//        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy, sortDirection),HttpStatus.OK);
//
//    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(@RequestParam(value = "pageNumber" ,defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(value="pageSize", defaultValue="10",required=false)int pageSize,
                                                                @RequestParam(value="sortBy", defaultValue ="name", required = false)String sortBy,
                                                                @RequestParam(value = "sortDirection", defaultValue = "ASC",required = false)String sortDirection){


        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy, sortDirection),HttpStatus.OK);

    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
        return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }

    //upload user image
    @PostMapping("image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile image,
                                                     @PathVariable int userId) throws IOException {
           String imageName= fileService.uploadImage(image, imageUploadPath);

           UserDto user = userService.getUserById(userId);

           user.setImageName(imageName);

           UserDto updateduser =userService.updateUser(user,userId);

           ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).message("Uploded succesfuly").build();

           return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable int userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("UserImage name: {}"+ user.getImageName());
        InputStream inputStream = fileService.getResource(imageUploadPath,user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
