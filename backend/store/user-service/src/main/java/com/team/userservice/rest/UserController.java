package com.team.userservice.rest;

import com.team.userservice.data.User;
import com.team.userservice.dto.UserRequestDto;
import com.team.userservice.dto.UserResponseDto;
import com.team.userservice.mapper.impl.UserRequestMapper;
import com.team.userservice.mapper.impl.UserResponseMapper;
import com.team.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserRequestMapper userRequestMapper;
  private final UserResponseMapper userResponseMapper;

  @GetMapping
  public ResponseEntity<List<UserResponseDto>> getAll() {
    List<UserResponseDto> userResponseDtos = userService.findAll().stream()
      .map(userResponseMapper::map)
      .toList();
    return ResponseEntity.ok().body(userResponseDtos);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
    UserResponseDto userResponseDto = userResponseMapper.map(userService.findById(id));
    return ResponseEntity.ok().body(userResponseDto);
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
    User user = userRequestMapper.map(userRequestDto);
    userService.save(user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                @RequestBody UserRequestDto userRequestDto) {
    User user = userRequestMapper.map(userRequestDto);
    user.setId(id);
    userService.update(user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<UserResponseDto> delete(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
