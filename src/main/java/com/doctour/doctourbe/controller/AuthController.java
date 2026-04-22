package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.*;
import com.doctour.doctourbe.model.*;
import com.doctour.doctourbe.repository.RoleRepository;
import com.doctour.doctourbe.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailSevice emailService;

    @Autowired
    private EncodingService encodingService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest registerRequest) throws UsernameException, PasswordException {
        AppUser appUser = new AppUser(registerRequest.email, registerRequest.password);
        Role role = roleRepository.findByName(registerRequest.role)
                .orElseThrow(() -> new RoleException("INVALID_ROLE"));
        appUser.setRoles(List.of(role));
        appUserService.save(appUser);
        return ResponseEntity.ok("User registered succesfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(),
                        loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = appUserService.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));

        if (appUser.getStatus() != AppUser.AppUserStatus.ACTIVE) {
            throw new AppUserException("USER_NOT_ACTIVE");
        }

        String accessToken = jwtService.generateAccessToken(appUser);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUser);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> invite (@RequestBody @Valid RegisterRequest registerRequest) throws PasswordException, EmailException {
        AppUser appUser = appUserService.createPending(registerRequest.email, registerRequest.username, registerRequest.password);
        VerificationToken token = verificationTokenService.createToken(appUser, VerificationToken.TokenType.REGISTRATION);
        emailService.sendInviteLink(appUser, token.getToken());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestBody @Valid ConfirmRequest confirmRequest) {
        VerificationToken token = verificationTokenService.validateToken(confirmRequest.token(), VerificationToken.TokenType.REGISTRATION);
        appUserService.activate(token.getAppUser());
        verificationTokenService.consumeToken(token);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        return refreshTokenService.findValidToken(refreshToken)
                .map(rt -> {
                    String newAccessToken = jwtService.generateAccessToken(
                            rt.getAppUser());
                    return ResponseEntity.ok(newAccessToken);
                })
                .orElseThrow(() -> new TokenException("INVALID"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        if (refreshToken != null) {
            refreshTokenService.findValidToken(refreshToken)
                    .ifPresent(rt -> refreshTokenService.deleteByAppUser(
                            rt.getAppUser()));

        }

        Cookie expiredCookie = new Cookie("refreshToken", "");
        expiredCookie.setHttpOnly(true);
        expiredCookie.setSecure(true);
        expiredCookie.setPath("/api/auth/refresh");
        expiredCookie.setMaxAge(0);
        response.addCookie(expiredCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<String> resetPassword(@RequestBody ResetRequest req) {

        AppUser appUser = appUserService.findByEmail(req.email).orElseThrow(() -> new AppUserException("NOT_FOUND"));

        VerificationToken token = verificationTokenService.createToken(appUser, VerificationToken.TokenType.PASSWORD_RESET);
        emailService.sendResetLink(appUser, token.getToken());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<?> validate(@RequestParam String token) {
        VerificationToken vt = verificationTokenService.validateToken(token, VerificationToken.TokenType.PASSWORD_RESET);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirm(@RequestBody @Valid ConfirmRequest confirmRequest) throws PasswordException {
        VerificationToken token = verificationTokenService.validateToken(confirmRequest.token(), VerificationToken.TokenType.PASSWORD_RESET);
        appUserService.changePassword(token.getAppUser(), confirmRequest.password());
        verificationTokenService.consumeToken(token);
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(
            @NotNull @Email String email,
            @NotNull String password,
            @NotNull String username
    ) {
    }

    public record ResetRequest(
            @NotNull @Email String email
    ) {
    }

    public record RegisterRequest(
            @NotNull @Email String email,
            @NotNull String username,
            @NotNull String password,
            @NotNull String role
    ) {
    }

    public record ConfirmRequest(
            @NotNull String token,
            @NotNull String password
    ){}
}
