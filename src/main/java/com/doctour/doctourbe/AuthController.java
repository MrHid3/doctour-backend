package com.doctour.doctourbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public AuthController(AppUserService appUserService, CustomAuthenticationProvider customAuthenticationProvider){
        this.appUserService = appUserService;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody AppUser appUser){
        try{
            appUserService.saveAppUser(appUser);
            return ResponseEntity.ok("User registered succesfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest loginRequest){
        try{
            Authentication authentication = customAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login succesfull");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

}
