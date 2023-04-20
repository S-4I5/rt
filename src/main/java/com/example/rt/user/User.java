package com.example.rt.user;

import com.example.rt.user.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    //@Length(min = 1, max = 25, message = "Firstname's length is incorrect")
    private String firstname;
    //@Length(min = 1, max = 25, message = "Lastname's length is incorrect")
    private String lastname;
    //@Email(message = "Email is incorrect")
    private String email;
    //@Length(min = 6, max = 30, message = "Lastname's length is incorrect")
    private String password;
    //@Enumerated(EnumType.STRING
    private Role role;
    private String photo;
    private String phone_number;
    private Date birthday_date;
    private boolean isMember;
    private long balance;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
