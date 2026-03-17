package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.InvalidPasswordException;
import com.doctour.doctourbe.exception.UsernameTakenException;
import com.doctour.doctourbe.model.*;
import com.doctour.doctourbe.repository.RoleRepository;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.CustomAuthenticationProvider;
import com.doctour.doctourbe.service.JwtService;
import com.doctour.doctourbe.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AppUserService appUserService, CustomAuthenticationProvider customAuthenticationProvider, JwtService jwtService, RefreshTokenService refreshTokenService, RoleRepository roleRepository){
        this.appUserService = appUserService;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest registerRequest){
        try{
            AppUser appUser = new AppUser(registerRequest.getUsername(), registerRequest.getPassword());
            Role role = roleRepository.findByName(registerRequest.getRole())
                            .orElseThrow(() -> new RoleNotFoundException("Invalid role"));
            appUser.setRoles(Arrays.asList(role));
            appUserService.saveAppUser(appUser);
            return ResponseEntity.ok("User registered succesfully");
        }catch (RoleNotFoundException e){
            return ResponseEntity.badRequest().body("Invalid role");
        }catch (UsernameTakenException e){
            return ResponseEntity.badRequest().body("Username taken");
        }catch (InvalidPasswordException e){
            return ResponseEntity.badRequest().body("Invalid password");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        try{
            Authentication authentication = customAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AppUser appUser = appUserService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String accessToken = jwtService.generateAccessToken(appUser);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(appUser);

            Cookie refreshCookie = new Cookie("refreshToken", refreshToken.getToken());
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true);
            refreshCookie.setPath("/api/auth/refresh");
            refreshCookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(refreshCookie);

            return ResponseEntity.ok(accessToken);
        }catch(UsernameNotFoundException e){
            return ResponseEntity.badRequest().body("Username not found");
        }catch(AuthenticationException e){
            return ResponseEntity.badRequest().body("Wrong username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh (@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        if (refreshToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token");
        }

        return refreshTokenService.findValidToken(refreshToken)
                .map(rt -> {
                    String newAccessToken = jwtService.generateAccessToken(
                            rt.getAppUser());
                    return ResponseEntity.ok(newAccessToken);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired access token"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        if (refreshToken != null){
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

        return ResponseEntity.ok("Logged out successfully");
    }
}
