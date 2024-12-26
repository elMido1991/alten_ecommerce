package com.ecommerce.business.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.ecommerce.abstractions.AbstractEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.AbstractCollection;
import java.util.Locale;

@Table("user_locale")
@Data
public class UserLocale extends AbstractEntity<Long> {

    @Id  // Primary key field
    @Column("user_id")
    private Long userId;

    @Column("locale")
    private String locale;

    public UserLocale(Long userId, Locale locale) {
        this.userId = userId;
        this.locale = locale.toLanguageTag();
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale.toLanguageTag();
    }
}
