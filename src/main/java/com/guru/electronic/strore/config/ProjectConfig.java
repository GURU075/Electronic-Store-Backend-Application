package com.guru.electronic.strore.config;

import com.guru.electronic.strore.dtos.UserDto;
import com.guru.electronic.strore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public ModelMapper mapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
            mapper.skip(UserDto::setPassword);
        });

        return new ModelMapper();
    }
}
