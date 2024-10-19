package com.guru.electronic.strore.exceptions;

import lombok.Builder;

@Builder
public class DuplicateEmail extends RuntimeException {
    public DuplicateEmail(String message){
         super(message);
    }

    public DuplicateEmail(){
        super("user with this email already exist");
    }
}
