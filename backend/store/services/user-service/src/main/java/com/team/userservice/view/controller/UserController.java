package com.team.userservice.view.controller;

import com.team.jwt.authentication.JwtAuthenticationToken;
import com.team.jwt.properties.TokenMetadata;
import com.team.jwt.service.JwtSecurityProvider;
import com.team.userservice.view.dto.UserDto;
import com.team.userservice.model.User;
import com.team.userservice.service.contract.UserService;
import com.team.userservice.infrastructure.kafka.ActivationSender;
import com.team.userservice.service.impl.TokenProvider;
import com.team.userservice.service.impl.UrlMatcher;
import com.team.userservice.view.MapperFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
  private final UserService userService;
  private final UrlMatcher urlMatcher;
  private final ActivationSender activationSender;
  private final TokenProvider tokenProvider;
  private final MapperFacade mapperFacade;
  private final JwtSecurityProvider securityProvider;
  private final TokenMetadata tokenMetadata;

  @Autowired
  public UserController(UserService userService,
                        MapperFacade mapperFacade,
                        UrlMatcher urlMatcher,
                        ActivationSender activationSender,
                        TokenProvider tokenProvider,
                        JwtSecurityProvider securityProvider,
                        TokenMetadata tokenMetadata) {
    this.userService = userService;
    this.urlMatcher = urlMatcher;
    this.activationSender = activationSender;
    this.tokenProvider = tokenProvider;
    this.mapperFacade = mapperFacade;
    this.securityProvider = securityProvider;
    this.tokenMetadata = tokenMetadata;
  }

  @GetMapping
  public ResponseEntity<List<UserDto.Response.Common>> getAll() {
    var userResponseDtos = userService.findAll().stream()
      .map(mapperFacade::toCommonResponseDto)
      .toList();
    return ResponseEntity.ok().body(userResponseDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> get(@PathVariable Long id) {
    var userResponseDto = mapperFacade.toCommonResponseDto(userService.findById(id));
    return ResponseEntity.ok().body(userResponseDto);
  }

  @PostMapping
  public ResponseEntity<UserDto.Response.Common> create(HttpServletRequest httpServletRequest,
                                                        @Valid @RequestBody UserDto.Request.Common userRequestDto) {
    User user = mapperFacade.commonRequestToDomain(userRequestDto);
    userService.create(user);

    String urlRoot = urlMatcher.getUrlRoot(httpServletRequest);
    UserDto.Response.Activation dto = mapperFacade.toActivationResponseDto(user, urlRoot);
    String message = mapperFacade.activationDtoToKafkaMessage(dto);
    activationSender.sendActivation("t.activation.link", message);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> update(@PathVariable Long id,
                                                        @Valid @RequestBody UserDto.Request.Update userRequestDto) {
    User userWithEmptyFields = mapperFacade.updateRequestToDomain(userRequestDto);
    userService.update(id, userWithEmptyFields);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/email")
  public ResponseEntity<UserDto.Response.Common> updateEmail(JwtAuthenticationToken authenticationToken,
                                                             HttpServletRequest request,
                                                             @Valid @RequestBody UserDto.Request.UpdateEmail userRequestDto) {
    String token = authenticationToken.getPrincipal();
    Long id = securityProvider.parseUserId(token, tokenMetadata);
    userService.update(id, User.builder().active(false).email(userRequestDto.getEmail()).build());
    User user = userService.findById(id);

    String urlRoot = urlMatcher.getUrlRoot(request);
    UserDto.Response.Activation dto = mapperFacade.toActivationResponseDto(user, urlRoot);
    String message = mapperFacade.activationDtoToKafkaMessage(dto);
    activationSender.sendActivation("t.activation.update", message);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> delete(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/activate")
  public ResponseEntity<?> activate(@RequestParam @NotNull String activateToken) {
    if (!tokenProvider.isValidToken(activateToken)) {
      throw new IllegalArgumentException("Activation token is invalid");
    }
    String email = tokenProvider.obtainEmail(activateToken);
    userService.activate(email);
    return ResponseEntity.ok().build();
  }
}
