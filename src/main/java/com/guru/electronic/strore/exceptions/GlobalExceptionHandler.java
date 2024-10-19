package com.guru.electronic.strore.exceptions;

import com.guru.electronic.strore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //handler resource not found exception

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){


        logger.info("Global exception handler invoked");
        ApiResponseMessage responseMessage =ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateEmail.class)
    public ResponseEntity<ApiResponseMessage> duplicateEmailException(DuplicateEmail ex){
        logger.info("DuplicateEmail method invoked");
        ApiResponseMessage message = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(true).build();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex){
          logger.info("Bad Api Request!!");
          ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

      //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String , Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message =objectError.getDefaultMessage();
            String field =((FieldError)objectError).getField();
            response.put(field,message);

        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
