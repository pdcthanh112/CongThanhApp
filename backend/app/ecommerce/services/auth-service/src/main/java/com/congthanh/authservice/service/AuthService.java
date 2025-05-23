package com.congthanh.authservice.service;

import com.congthanh.authservice.model.request.*;
import com.congthanh.authservice.model.response.AuthResponse;
import com.congthanh.authservice.model.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    void signup(SignupRequest signupRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void logout(String token);

    AuthResponse refreshAccessToken(RefreshAccessTokenRequest request);
}
