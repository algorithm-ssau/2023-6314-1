package com.team.userservice.rest;

import com.team.userservice.data.User;
import com.team.userservice.dto.UserDto;
import com.team.userservice.mapper.UserMapper;
import com.team.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper.Request.Common commonRequestUserMapper;
  private final UserMapper.Response.Common commonResponseUserMapper;

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
  public ResponseEntity<UserDto.Response.Common> create(
    @Valid @RequestBody UserDto.Request.Common userRequestDto
  ) {
    var encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
    User user = commonRequestUserMapper.toDomain(userRequestDto, encodedPassword);
    userService.create(user);
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
}
