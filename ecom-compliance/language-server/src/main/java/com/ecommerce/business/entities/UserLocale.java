package com.ecommerce.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Locale;


@Table("user_locale")
@Data
@AllArgsConstructor
public class UserLocale {
    @Id
    @Column("user_id")
    private Long userId;
    @Id
    @Column("locale")
    private Locale locale;
}
