package com.epam.facultative.dto;

import com.epam.facultative.entity.Role;

import java.util.Objects;

public class UserDTO {
    private int id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private boolean isBlock;
    private Role role;

    public UserDTO() {
    }

    public UserDTO(int id, String login, String name, String surname, String email, boolean isBlock, Role role) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isBlock = isBlock;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && isBlock == userDTO.isBlock && Objects.equals(login, userDTO.login) && Objects.equals(name, userDTO.name) && Objects.equals(surname, userDTO.surname) && Objects.equals(email, userDTO.email) && role == userDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, email, isBlock, role);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", isBlock=" + isBlock +
                ", role=" + role +
                '}';
    }
}
