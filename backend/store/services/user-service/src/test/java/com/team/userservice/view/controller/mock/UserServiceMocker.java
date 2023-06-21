package com.team.userservice.view.controller.mock;

import com.team.userservice.model.User;
import com.team.userservice.service.contract.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

public class UserServiceMocker {
  private final UserService userService;

  public UserServiceMocker(UserService userService) {
    this.userService = userService;
  }

  public void findAllMock(List<User> userList) {
    doReturn(userList)
      .when(userService)
      .findAll();
  }

  public void findByIdMock(List<User> userList) {
    doAnswer(invoc -> userList.get((int) (long) invoc.getArgument(0, Long.class)))
      .when(userService)
      .findById(anyLong());
  }

  public void createMock(List<User> userList) {
    doAnswer(invoc -> userList.add(invoc.getArgument(0, User.class)))
      .when(userService)
      .create(any(User.class));
  }

  public void updateMock(List<User> userList) {
    doAnswer(invoc -> {
      User old = userList.get((int) (long) invoc.getArgument(0, Long.class));
      User next = invoc.getArgument(1, User.class);
      old.setId(next.getId());
      old.setName(next.getName());
      old.setRole(next.getRole());
      old.setActive(next.getActive());
      old.setEmail(next.getEmail());
      old.setPassword(next.getPassword());
      return null;
    }).when(userService)
      .update(anyLong(), any(User.class));
  }

  public void deleteByIdMock(List<User> userList) {
    doAnswer(invoc -> {
      int index = (int) (long) invoc.getArgument(0, Long.class);
      userList.remove(index);
      return null;
    }).when(userService).deleteById(anyLong());
  }

  public void activateUserByEmailMock(List<User> userList) {
    doAnswer(invoc -> {
      String requiredEmail = invoc.getArgument(0, String.class);
      User requiredUser = userList.stream()
        .filter(u -> u.getEmail().equals(requiredEmail))
        .findAny()
        .orElseThrow(IllegalArgumentException::new);

      if (requiredUser.getActive()) {
        throw new IllegalArgumentException();
      }
      requiredUser.setActive(true);

      return null;
    }).when(userService).activateUserByEmail(anyString());
  }
}
