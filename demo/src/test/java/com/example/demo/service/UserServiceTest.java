package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.domain.User;
import com.example.demo.dto.responses.user_responses.UserResponse;
import com.example.demo.exception.SameEmailException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserisAlreadyInactiveException;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Test
  public void testCreateUser() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponse mockResponse =
        userService.createUser(user.getAge(), user.getUsername(), user.getEmail());

    assertNotNull(mockResponse);
    assertEquals(user.getUsername(), mockResponse.getUsername());

    verify(userRepository).save(any(User.class));
  }

  @Test
  public void testCreateUserThrowsUserAlreadyExistsException() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

    assertThrows(
        UserAlreadyExistsException.class,
        () -> userService.createUser(user.getAge(), user.getUsername(), user.getEmail()));
  }

  @Test
  public void testFindById() {

    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    User mockUser = userService.findUserById(user.getId());

    assertNotNull(mockUser);
    assertEquals(user.getEmail(), mockUser.getEmail());
    assertEquals(user.getAge(), mockUser.getAge());
    assertEquals(user.getIsActive(), mockUser.getIsActive());
  }

  @Test
  public void testFindByIdThrowsUserNotFoundException() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.findById(user.getId()));
  }

  @Test
  public void testUpdateUserEmail() {

    String new_email = "whatever@hotmail.com";
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponse mockResponse = userService.updateUserEmail(user.getId(), new_email);

    assertNotNull(mockResponse);
    assertEquals(user.getId(), mockResponse.getId());
    assertEquals(user.getUsername(), mockResponse.getUsername());
    assertEquals(new_email, mockResponse.getEmail());
    assertEquals(user.getAge(), mockResponse.getAge());
    assertEquals(user.getIsActive(), mockResponse.isActive());
  }

  @Test
  public void testUpdateUserEmailThrowsUserNotFoundException() {
    String new_email = "whatever@hotmail.com";
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);

    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

    assertThrows(
        UserNotFoundException.class, () -> userService.updateUserEmail(user.getId(), new_email));
  }

  @Test
  public void testUpdateUserEmailThrowsSameEmailException() {

    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);
    String new_email = user.getEmail();

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    assertThrows(
        SameEmailException.class, () -> userService.updateUserEmail(user.getId(), user.getEmail()));
  }

  @Test
  public void testStUserStatusToInactive() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);
    user.setId(11L);

    when(userRepository.findById(11L)).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);

    UserResponse mockUserResponse = userService.setUserStatusToInactive(user.getId());

    assertNotNull(mockUserResponse);
    assertEquals(mockUserResponse.isActive(), false);
    assertEquals(mockUserResponse.getUsername(), "steve");
  }

  @Test
  public void testUserStatusToInactiveThrowsUserNotFoundException() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, true);
    user.setId(11L);

    when(userRepository.findById(11L)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.setUserStatusToInactive(11L));
  }

  @Test
  public void testUserStatusToInactiveThrowsUserIsAlreadyInactiveException() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, false);
    user.setId(11L);

    when(userRepository.findById(11L)).thenReturn(Optional.of(user));

    assertThrows(
        UserisAlreadyInactiveException.class, () -> userService.setUserStatusToInactive(11L));
  }

  @Test
  public void testDeleteById() {
    User user = new User("steve", "stevekalelis@hotmail.com", 26, false);
    user.setId(11L);

    when(userRepository.findById(11L)).thenReturn(Optional.of(user));

    UserResponse mockResponse = userService.deleteById(11L);

    assertNotNull(mockResponse);
    verify(userRepository, times(1)).deleteById(11L);
  }
}
