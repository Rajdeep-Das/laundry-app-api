package com.azure.laundry.laundry.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  Logger logger = LoggerFactory.getLogger(AuthController.class);

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

  @Autowired
  private JavaMailSender mailSender;

  /*
   * ------------- Request Mappings --------
   * Todo: Refactor To Service and Cleanup
   */
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

    // return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
    // userDetails.getId(),
    // userDetails.getUsername(), userDetails.getEmail(), roles));

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(), userDetails.getPhone(), userDetails.isEmailVerified(),
        userDetails.isPhoneVerified(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) {
    // if (userRepository.existsByUsername(signUpRequest.getUsername())) {
    // return ResponseEntity.badRequest().body(new MessageResponse("Error: Username
    // is already taken!"));
    // }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    // User user = new User(signUpRequest.getUsername(),
    // signUpRequest.getEmail(),signUpRequest.getPhone(),
    // encoder.encode(signUpRequest.getPassword()));

    User user = new User(signUpRequest.getEmail(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    // Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    /*
     * if (strRoles == null) {
     * Role userRole = roleRepository.findByName(ERole.ROLE_USER)
     * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
     * roles.add(userRole);
     * } else {
     * strRoles.forEach(role -> {
     * switch (role) {
     * case "admin":
     * Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
     * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
     * roles.add(adminRole);
     * 
     * break;
     * case "mod":
     * Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
     * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
     * roles.add(modRole);
     * 
     * break;
     * default:
     * Role userRole = roleRepository.findByName(ERole.ROLE_USER)
     * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
     * roles.add(userRole);
     * }
     * });
     * }
     */

    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);
    user.setRoles(roles);
    // Add Phone
    user.setPhone(signUpRequest.getPhone());
    // Email Verification Setps
    String randomCode = RandomString.make(64);
    user.setVerificationCode(randomCode);
    user.setEmailVerified(false);
    // Save To DB
    userRepository.save(user);

    try {
      sendVerificationEmail(user, getSiteURL(request));
      logger.error("Email Send To {email}", user.getEmail());
    } catch (UnsupportedEncodingException e) {
      logger.error("Email Sendin Failed ...{msg}", e.getMessage());
      e.printStackTrace();
    } catch (MessagingException e) {
      logger.error("Email Sendin Failed ...{msg}", e.getMessage());
      e.printStackTrace();
    }

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

  @GetMapping("/verify")
  public String verifyUser(@Param("code") String code) {
    User user = userRepository.findByVerificationCode(code);
    if (user == null || user.isEmailVerified()) {
      return "<h3>Sorry, we could not verify account. It maybe already verified,or verification request is invalid.</h3>";
    } else {
      user.setVerificationCode(null);
      user.setEmailVerified(true);
      userRepository.save(user);
      return "<h3>Congratulations, your account has been verified.</h3>";
    }

  }

  /*
   * ------------- Helper Methods Later Refactor To Proper Service --------
   * 
   */
  private String getSiteURL(HttpServletRequest request) {
    String siteURL = request.getRequestURL().toString();
    return siteURL.replace(request.getServletPath(), "");
  }

  // Send Verification Email
  private void sendVerificationEmail(User user, String siteURL)
      throws MessagingException, UnsupportedEncodingException {
    String toAddress = user.getEmail();
    String fromAddress = "azure.digitalbank@gmail.com";
    // String fromAddress = "support@atomslearning.com";
    String senderName = "Azure Digital Bank";
    String subject = "Please verify your registration";
    String content = "Dear [[name]],<br>"
        + "Please click the link below to verify your registration:<br>"
        + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
        + "Thank you,<br>"
        + "Azure India Laundry.";

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);

    // Replace With Name Later
    content = content.replace("[[name]]", user.getEmail());
    // Path
    String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();

    content = content.replace("[[URL]]", verifyURL);

    helper.setText(content, true);

    mailSender.send(message);
  }

}
