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
    //@JsonSetter(nulls = Nulls.SKIP)
    private String firstname;
    //@JsonSetter(nulls = Nulls.SKIP)
    private String lastname;
    //@JsonSetter(nulls = Nulls.SKIP)
    private String email;
    //@JsonSetter(nulls = Nulls.SKIP)
    private String password;
    //@JsonSetter(nulls = Nulls.SKIP)
    private String photo;
    //@JsonSetter(nulls = Nulls.SKIP)
    private Date birthdayDate;
    //@JsonSetter(nulls = Nulls.SKIP)
    private String phoneNumber;
}
