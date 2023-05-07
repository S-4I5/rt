package com.example.rt.user.dto;

import com.example.rt.user.Role;

import java.util.Date;

public record UserDTO (
         String firstname,
         String lastname,
         String email,
         String photo,
         Date birthdayDate,
         String phoneNumber,
         long balance,
         Role role
){
}
