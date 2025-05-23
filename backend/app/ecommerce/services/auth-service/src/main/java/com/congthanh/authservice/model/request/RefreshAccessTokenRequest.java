package com.congthanh.authservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshAccessTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
