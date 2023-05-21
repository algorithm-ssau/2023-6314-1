package com.team.userservice.view.controller;

import com.team.userservice.view.dto.UserDto;
import com.team.userservice.model.User;
import com.team.userservice.service.contract.UserService;
import com.team.userservice.service.impl.ActivationSender;
import com.team.userservice.service.impl.TokenProvider;
import com.team.userservice.service.impl.UrlMatcher;
import com.team.userservice.view.MapperFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UrlMatcher urlMatcher;
  private final ActivationSender activationSender;
  private final TokenProvider tokenProvider;
  private final MapperFacade mapperFacade;

  @Autowired
  public UserController(UserService userService,
                        MapperFacade mapperFacade,
                        UrlMatcher urlMatcher,
                        ActivationSender activationSender,
                        TokenProvider tokenProvider) {
    this.userService = userService;
    this.urlMatcher = urlMatcher;
    this.activationSender = activationSender;
    this.tokenProvider = tokenProvider;
    this.mapperFacade = mapperFacade;
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
    activationSender.sendActivation(message);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto.Response.Common> update(@PathVariable Long id,
                                                        @Valid @RequestBody UserDto.Request.Update userRequestDto) {
    User userWithEmptyFields = mapperFacade.updateRequestToDomain(userRequestDto);
    userService.update(id, userWithEmptyFields);
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
