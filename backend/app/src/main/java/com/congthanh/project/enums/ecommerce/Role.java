package com.congthanh.project.enums.ecommerce;

public enum Role {

    ADMIN("admin"),
    USER("user");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }

}
