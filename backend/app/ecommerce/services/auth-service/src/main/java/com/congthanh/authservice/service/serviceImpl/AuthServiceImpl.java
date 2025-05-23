package com.congthanh.authservice.service.serviceImpl;

import com.congthanh.authservice.model.entity.*;
import com.congthanh.authservice.model.request.*;
import com.congthanh.authservice.model.response.AuthResponse;
import com.congthanh.authservice.model.response.LoginResponse;
import com.congthanh.authservice.repository.account.AccountRepository;
import com.congthanh.authservice.repository.loginError.LoginErrorRepository;
import com.congthanh.authservice.repository.refreshToken.RefreshTokenRepository;
import com.congthanh.authservice.service.AuthService;
import com.congthanh.authservice.service.JwtService;
import com.congthanh.authservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    private final LoginErrorRepository loginErrorRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long INITIAL_LOCKOUT_DURATION = 30 * 60 * 1000;

    @Override
    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email not found"));

        LoginError findError = loginErrorRepository.findByAccountId(account.getAccountId());

        if (findError != null && findError.getLockedUntil().isAfter(Instant.now())) {
            throw new RuntimeException("Your account is locked until " + findError.getLockedUntil());
        }

//        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
//                findError.setFailedAttempts(findError.getFailedAttempts() > 0 ? findError.getFailedAttempts() + 1:  1);
//
//
//            if (findError.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
//                lockAccount(user);
//            }
//            userRepository.save(user);
//            logActivity(user, "LOGIN_ATTEMPT", "Failed login attempt. Attempts: " + user.getFailedAttempts());
//            throw new RuntimeException("Invalid credentials. Attempts remaining: " + (MAX_FAILED_ATTEMPTS - user.getFailedAttempts()));
//        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

//        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String token = jwtService.generateAccessToken(new TokenPayload(account.getAccountId(), List.of("USER")));
        String refreshToken = jwtService.generateRefreshToken(account.getAccountId());

//        RefreshToken r = RefreshToken.builder()
//                .id(snowflakeIdGenerator.nextId())
//                .account(account)
//                .token(token)
//                .expiryDate(Instant.now().plusSeconds(30*24*60*60))
//                .createdAt(Instant.now())
//                .build();
//        refreshTokenRepository.save(r);

//        logActivity(user, "LOGIN_SUCCESS", "User logged in");
        return new LoginResponse(token, refreshToken, "Bearer", LoginResponse.UserInfo.builder().build());
    }

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<Account> account = accountRepository.findByEmail(signupRequest.getEmail());
        if (account.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String passwordHashed = passwordEncoder.encode(signupRequest.getPassword());

        Account newAccount = Account.builder()
                .accountId("8950386985")
                .email(signupRequest.getEmail())
                .password(passwordHashed)
                .build();

        Account result = accountRepository.save(newAccount);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<Account> userOpt = accountRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (userOpt.isPresent()) {
            Account user = userOpt.get();
//            String resetToken = UUID.randomUUID().toString();
            // In real app, send resetToken via email
//            user.setPassword(resetToken); // Store token temporarily (replace with email service)
//            userRepository.save(user);
//            logActivity(user, "FORGOT_PASSWORD", "Reset token generated: " + resetToken);
        } else {
//            logActivity(null, "FORGOT_PASSWORD_ATTEMPT", "Email not found: " + forgotPasswordRequest.getEmail());
        }
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<Account> userOpt = accountRepository.findByEmail(resetPasswordRequest.getToken());
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
//            user.setFailedAttempts(0);
//            userRepository.save(user);
//            logActivity(user, "RESET_PASSWORD", "Password reset successful");
//        } else {
//            throw new RuntimeException("Invalid or expired reset token");
//        }
    }

    @Override
    @Transactional
    public void logout(String token) {
        String accountId = jwtService.getAccountIdFromToken(token);
        Optional<Account> userOpt = accountRepository.findByAccountId(accountId);
        if (userOpt.isPresent()) {
            Account user = userOpt.get();
            jwtService.blacklistToken(token);
//            logActivity(user, "LOGOUT", "User logged out");
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    @Transactional
    public AuthResponse refreshAccessToken(RefreshAccessTokenRequest request) {
        String accountId = jwtService.getAccountIdFromToken(request.getRefreshToken());
        Optional<Account> userOpt = accountRepository.findByAccountId(accountId);
        if (userOpt.isPresent()) {
            Account user = userOpt.get();
            String newAccessToken = jwtService.refreshAccessToken(request.getRefreshToken());
//            logActivity(user, "REFRESH_TOKEN", "Access token refreshed");
            return new AuthResponse(newAccessToken, request.getRefreshToken());
        } else {
            throw new RuntimeException("User not found");
        }
    }

//    private void lockAccount(User user) {
//        if (!user.isLocked()) {
//            user.setLocked(true);
//            user.setLockoutUntil(LocalDateTime.now().plusSeconds(calculateLockoutDuration(user.getFailedAttempts())));
//            logActivity(user, "ACCOUNT_LOCKED", "Account locked until " + user.getLockoutUntil() + ". Attempts: " + user.getFailedAttempts());
//        } else if (user.getLockoutUntil().isBefore(LocalDateTime.now())) {
//            user.setLocked(false);
//            user.setLockoutUntil(null);
//            logActivity(user, "ACCOUNT_UNLOCKED", "Account automatically unlocked");
//        }
//    }

    private long calculateLockoutDuration(int failedAttempts) {
        return INITIAL_LOCKOUT_DURATION * (long) Math.pow(2, failedAttempts - MAX_FAILED_ATTEMPTS);
    }

//    private void logActivity(User user, String action, String details) {
//        ActivityLog log = new ActivityLog();
//        log.setUser(user);
//        log.setAction(action);
//        log.setTimestamp(LocalDateTime.now());
//        log.setDetails(details);
//        activityLogRepository.save(log);
//    }
}
