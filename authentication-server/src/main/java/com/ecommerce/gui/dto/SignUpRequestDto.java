package com.ecommerce.gui.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignUpRequestDto extends LoginRequestDto {
    private String username;
    private String firstname;
}
