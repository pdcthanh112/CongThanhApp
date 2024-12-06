package com.congthanh.storefrontbff.model;

public record AuthenticationInfo(boolean isAuthenticated, AuthenticatedUser authenticatedUser) {
}
