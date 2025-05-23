package com.congthanh.authservice.service.serviceImpl;

import com.congthanh.authservice.model.entity.Account;
import com.congthanh.authservice.model.entity.Role;
import com.congthanh.authservice.model.entity.User;
import com.congthanh.authservice.repository.account.AccountRepository;
import com.congthanh.authservice.repository.role.RoleRepository;
import com.congthanh.authservice.repository.user.UserRepository;
import com.congthanh.authservice.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public Account processOAuth2User(String registrationId, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String firstName = "";
        String lastName = "";
        String providerId = "";

        if ("google".equalsIgnoreCase(registrationId)) {
            firstName = (String) attributes.get("given_name");
            lastName = (String) attributes.get("family_name");
            providerId = (String) attributes.get("sub");
        } else if ("facebook".equalsIgnoreCase(registrationId)) {
            String name = (String) attributes.get("name");
            String[] names = name.split(" ");
            firstName = names[0];
            if (names.length > 1) {
                lastName = names[names.length - 1];
            }
            providerId = (String) attributes.get("id");
        }

//        Account.AuthProvider provider = Account.AuthProvider.valueOf(registrationId.toUpperCase());

//        return accountRepository.findByProviderAndProviderId(provider, providerId)
//                .orElseGet(() -> {
//                    // Create User
//                    User user = new User();
//                    user.setFirstName(firstName);
//                    user.setLastName(lastName);
//                    User savedUser = userRepository.save(user);
//
//                    // Create Account
//                    Account account = new Account();
//                    account.setEmail(email);
//                    account.setProvider(provider);
//                    account.setProviderId(providerId);
//                    account.setIsVerified(true);
//                    account.setPassword(""); // OAuth users don't need password
//                    account.setUser(savedUser);
//
//                    Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role USER not found"));
//                    account.setRoles(Set.of(userRole));
//
//                    return accountRepository.save(account);
//                });
        return null;
    }
}
