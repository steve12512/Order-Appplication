package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private UserService userService;

  @Test
  void testAdvancedTestEndpoint() throws Exception {
    User user = new User("cccSTAVROS", "stevekalelis@outlook.gr", 40, true);
    Page<User> page = new PageImpl<>(List.of(user));

    Mockito.when(userService.getRankedUsers(Mockito.any())).thenReturn(page);

    mockMvc
        .perform(
            get("/users/filter/advanced_search")
                .param("minAge", "30")
                .param("maxAge", "50")
                .param("usernameContains", "ccc")
                .param("isActive", "true")
                .param("page", "0")
                .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].username").value("cccSTAVROS"))
        .andExpect(jsonPath("$.content[0].age").value(40))
        .andExpect(jsonPath("$.content[0].isActive").value(true));
  }
}
