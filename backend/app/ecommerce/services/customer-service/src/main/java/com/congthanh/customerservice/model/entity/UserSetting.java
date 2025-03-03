package com.congthanh.customerservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_setting")
public class UserSetting {

    @Id
    private Long id;

    private String userId;

//    @Column(columnDefinition = "json")
//    private Map<String, String> settings = new HashMap<>();

}
