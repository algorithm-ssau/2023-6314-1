package com.team.userservice.controller;

import com.team.userservice.service.impl.TokenBuilder;
import com.team.userservice.service.impl.ActivationSender;
import com.team.userservice.service.impl.TokenProvider;
import com.team.userservice.service.impl.UrlMatcher;
import com.team.userservice.model.User;
import com.team.userservice.dto.UserDto;
import com.team.userservice.mapper.UserMapper;
import com.team.userservice.service.contract.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper.Request.Common commonRequestUserMapper;
  private final UserMapper.Response.Common commonResponseUserMapper;
  private final UrlMatcher urlMatcher;
  private final ActivationSender activationSender;
  private final TokenBuilder tokenBuilder;
  private final TokenProvider tokenProvider;

  @Autowired
  public UserController(UserService userService,
                        PasswordEncoder passwordEncoder,
                        UserMapper.Request.Common commonRequestUserMapper,
                        UserMapper.Response.Common commonResponseUserMapper,
                        UrlMatcher urlMatcher,
                        ActivationSender activationSender,
                        TokenBuilder tokenBuilder,
                        TokenProvider tokenProvider) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.commonRequestUserMapper = commonRequestUserMapper;
    this.commonResponseUserMapper = commonResponseUserMapper;
    this.urlMatcher = urlMatcher;
    this.activationSender = activationSender;
    this.tokenBuilder = tokenBuilder;
    this.tokenProvider = tokenProvider;
  }

  @GetMapping
  public ResponseEntity<List<UserDto.Response.Common>> getAll() {
    var userResponseDtos = userService.findAll().stream()
      .map(commonResponseUserMapper::toDto)
      .toList();
    return ResponseEntity.ok().body(userResponseDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> get(@PathVariable Long id) {
    var userResponseDto = commonResponseUserMapper.toDto(userService.findById(id));
    return ResponseEntity.ok().body(userResponseDto);
  }

  @PostMapping
  public ResponseEntity<UserDto.Response.Common> create(HttpServletRequest httpServletRequest,
                                                        @Valid @RequestBody UserDto.Request.Common userRequestDto) {
    var encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
    User user = commonRequestUserMapper.toDomain(userRequestDto, encodedPassword);
    userService.create(user);

    String urlRoot = urlMatcher.getUrlRoot(httpServletRequest);
    String activateToken = tokenBuilder.buildTokenBody(user);
    String activationLink = urlRoot + "/api/users/activate?activateToken=" + activateToken;
    activationSender.sendActivation(user, activationLink);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> update(
    @PathVariable Long id,
    @Valid @RequestBody UserDto.Request.Common userRequestDto
  ) {
    var encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
    User user = commonRequestUserMapper.toDomain(userRequestDto, encodedPassword);
    user.setId(id);
    userService.update(user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> delete(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value =  "/activate")
  public ResponseEntity<?> activate(@RequestParam String activateToken) {
    Objects.requireNonNull(activateToken, "Activation token is null");
    if (!tokenProvider.isValidToken(activateToken)) {
      throw new IllegalArgumentException("Activation token is invalid");
    }

    String email = tokenProvider.obtainEmail(activateToken);
    userService.activate(email);
    return ResponseEntity.ok().build();
  }
}
