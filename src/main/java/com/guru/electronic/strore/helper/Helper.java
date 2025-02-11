package com.guru.electronic.strore.helper;

import com.guru.electronic.strore.dtos.PageableResponse;
import com.guru.electronic.strore.dtos.UserDto;
import com.guru.electronic.strore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){
        List<U> entity =  page.getContent();
        List<V> DtoList = entity.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(DtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElemants(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
