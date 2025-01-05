package com.congthanh.customerservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Country")
public class Country {

    @Id
    private Long id;

    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "phone_code")
    private String phoneCode;

    private String currency;

}
