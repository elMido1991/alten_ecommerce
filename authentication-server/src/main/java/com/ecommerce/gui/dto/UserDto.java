package com.ecommerce.gui.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends SignUpRequestDto {
    private Long id;
}
