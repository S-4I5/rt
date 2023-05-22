package com.example.rt.user.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChangeUserInfoRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String photo;
    private Date birthdayDate;
    private String phoneNumber;
}
