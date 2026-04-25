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
    private RoleService roleService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EncodingService encodingService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest req) throws UsernameException, PasswordException {
        AppUser appUser;

        appUser = appUserService.createPending(req.email, req.username, req.password,
                roleService.findByName(req.role).orElseThrow(() -> new RoleException("INVALID")),
                genderService.findById(req.genderId).orElseThrow(() -> new GenderException("INVALID"))
        );

        VerificationToken vt = verificationTokenService.createToken(appUser, VerificationToken.TokenType.REGISTRATION);
        emailService.sendActiviationLink(appUser, vt.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email,
                        loginRequest.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = appUserService.findByEmail(loginRequest.email())
                .orElseThrow(() -> new EmailException("NOT_FOUND"));

        if (appUser.getStatus() != AppUser.AppUserStatus.ACTIVE) {
            throw new AppUserException("NOT_ACTIVE");
        }

        String accessToken = jwtService.generateAccessToken(appUser);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUser);

        Cookie rt = new Cookie("refreshToken", refreshToken.getToken());
        rt.setHttpOnly(true);
        rt.setSecure(false); //remove for prod
        rt.setPath("/api/auth/refresh");
        rt.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(rt);

        Cookie at = new Cookie("accessToken", accessToken);
        at.setPath("/");
        at.setSecure(false); //remove for prod
        at.setMaxAge(60 * 15);
        response.addCookie(at);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateUser(@RequestBody @Valid ConfirmRequest confirmRequest) {
        VerificationToken token = verificationTokenService.validateToken(confirmRequest.token(), VerificationToken.TokenType.REGISTRATION);
        appUserService.activate(token.getAppUser());
        verificationTokenService.consumeToken(token);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        return refreshTokenService.findValidToken(refreshToken)
                .map(rt -> {
                    String newAccessToken = jwtService.generateAccessToken(
                            rt.getAppUser());
                    Cookie at = new Cookie("accessToken", newAccessToken);
                    at.setHttpOnly(true);
                    at.setSecure(true);
                    at.setMaxAge(900);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new TokenException("INVALID"));
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
    public ResponseEntity<?> confirm(@RequestBody @Valid ConfirmResetRequest req) throws PasswordException {
        VerificationToken token = verificationTokenService.validateToken(req.token(), VerificationToken.TokenType.PASSWORD_RESET);
        appUserService.changePassword(token.getAppUser(), req.password());
        verificationTokenService.consumeToken(token);
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(
            @NotNull @Email String email,
            @NotNull String password
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
            @NotNull Long genderId,
            @NotNull String role,
            Long locationId
    ) {
    }

    public record ConfirmRequest(
            @NotNull String token
    ) {
    }

    public record ConfirmResetRequest(
            @NotNull String token,
            @NotNull String password
    ){}
}
