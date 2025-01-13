package com.congthanh.catalogservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country")
public class Country {

    @Id
    private Long id;

    @Column(name = "iso_code", nullable = false, length = 2, columnDefinition = "CHAR(2)")
    private String isoCode;

    @Column(name = "iso_code_3", length = 3, columnDefinition = "CHAR(3)")
    private String isoCode3;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "country_name_origin")
    private String countryNameOrigin;

    @Column(name = "phone_code", nullable = false, columnDefinition = "VARCHAR(10)")
    private String phoneCode;

    @Column(name = "currency_code", columnDefinition = "VARCHAR(10)")
    private String currencyCode;

    @Column(name = "currency_symbol", columnDefinition = "VARCHAR(10)")
    private String currencySymbol;

    @Column(columnDefinition = "VARCHAR(20)")
    private String language;

    @Column(columnDefinition = "VARCHAR(10)")
    private String locale;

    @Column(columnDefinition = "VARCHAR(10)")
    private String timezone;
}
