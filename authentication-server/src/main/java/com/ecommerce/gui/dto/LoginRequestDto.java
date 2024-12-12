package com.ecommerce.gui.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
