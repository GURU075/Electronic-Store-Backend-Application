package com.guru.electronic.strore.exceptions;

import lombok.Builder;

@Builder
public class BadApiRequestException extends RuntimeException{
      public BadApiRequestException(){
          super("Bad Request!!");
      }
      public BadApiRequestException(String message){
          super(message);
      }
}
