package com.azure.laundry.laundry.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.azure.laundry.laundry.exception.TokenRefreshException;
import com.azure.laundry.laundry.models.ERole;
import com.azure.laundry.laundry.models.RefreshToken;
import com.azure.laundry.laundry.models.Role;
import com.azure.laundry.laundry.models.User;
import com.azure.laundry.laundry.payload.request.LogOutRequest;
import com.azure.laundry.laundry.payload.request.LoginRequest;
import com.azure.laundry.laundry.payload.request.SignupRequest;
import com.azure.laundry.laundry.payload.request.TokenRefreshRequest;
import com.azure.laundry.laundry.payload.response.JwtResponse;
import com.azure.laundry.laundry.payload.response.MessageResponse;
import com.azure.laundry.laundry.payload.response.TokenRefreshResponse;
import com.azure.laundry.laundry.repository.RoleRepository;
import com.azure.laundry.laundry.repository.UserRepository;
import com.azure.laundry.laundry.security.jwt.JwtUtils;
import com.azure.laundry.laundry.security.services.RefreshTokenService;
import com.azure.laundry.laundry.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    // return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
    //     userDetails.getUsername(), userDetails.getEmail(), roles));

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(),userDetails.getPhone(),userDetails.isEmailVerified(),userDetails.isPhoneVerified(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    // if (userRepository.existsByUsername(signUpRequest.getUsername())) {
    //   return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    // }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    // User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),signUpRequest.getPhone(),
    //     encoder.encode(signUpRequest.getPassword()));

    User user = new User(signUpRequest.getEmail(),signUpRequest.getEmail(),
      encoder.encode(signUpRequest.getPassword()));

   // Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    /*if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }*/
    
    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);
    user.setRoles(roles);
    // Add Phone
    user.setPhone(signUpRequest.getPhone());
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
         // String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          String token = jwtUtils.generateTokenFromEmail(user.getEmail());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
    refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

}
