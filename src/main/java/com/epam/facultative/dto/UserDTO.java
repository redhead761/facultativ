package com.epam.facultative.dto;

import com.epam.facultative.data_layer.entities.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


@Data
@EqualsAndHashCode(of = {"id", "login"})
@SuperBuilder
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
