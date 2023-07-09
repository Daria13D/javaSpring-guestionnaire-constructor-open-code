package ru.arharova.questionnarie_constructor.controllers;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
