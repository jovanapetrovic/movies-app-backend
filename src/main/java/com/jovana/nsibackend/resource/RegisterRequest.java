package com.jovana.nsibackend.resource;

import com.jovana.nsibackend.util.Constants;

import javax.validation.constraints.*;

/**
 * Created by jovana on 05.11.2018
 */
public class RegisterRequest {
    @NotBlank
    @Size(min = Constants.NAME_MIN_LENGTH, max = Constants.NAME_MAX_LENGTH)
    private String name;

    @NotBlank
    @Size(min = Constants.USERNAME_MIN_LENGTH, max = Constants.USERNAME_MAX_LENGTH)
    private String username;

    @NotBlank
    @Size(max = Constants.EMAIL_MAX_LENGTH)
    @Email
    private String email;

    @NotBlank
    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
