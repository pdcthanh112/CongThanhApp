package com.congthanh.storefrontbff.controller;

import com.congthanh.storefrontbff.model.AuthenticatedUser;
import com.congthanh.storefrontbff.model.AuthenticationInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/authentication")
    public ResponseEntity<AuthenticationInfo> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.ok(new AuthenticationInfo(false, null));
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(principal.getAttribute("preferred_username"));
        return ResponseEntity.ok(new AuthenticationInfo(true, authenticatedUser));
    }

}
