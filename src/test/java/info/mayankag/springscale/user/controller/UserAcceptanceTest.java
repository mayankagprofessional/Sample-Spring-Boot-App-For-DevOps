package info.mayankag.springscale.user.controller;

import info.mayankag.springscale.security.jwt.JwtTokenService;
import info.mayankag.springscale.user.domain.User;
import info.mayankag.springscale.user.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;

    private String getUserAccessToken(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(value -> jwtTokenService.createJwtToken(value)).orElse(null);
    }

    @Test
    @Order(1)
    void register_200() throws Exception {
        String testData = "{\"email\":\"test@test.com\", \"password\":\"SecurePassword\",  \"age\": 30 }";
        mockMvc.perform(post("/api/user/register")
                        .content(testData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("User registered")));
    }

    @Test
    @Order(2)
    void login_200() throws Exception {
        String testData = "{\"email\":\"test@test.com\", \"password\":\"SecurePassword\"}";
        mockMvc.perform(post("/api/user/login")
                        .content(testData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User logged in successfully")));
    }

    @Test
    @Order(3)
    void updateEmail_200() throws Exception {
        String testData = "{\"oldEmail\":\"test@test.com\", \"newEmail\":\"test@test.com\"}";
        mockMvc.perform(patch("/api/user/updateEmail")
                        .header("Authorization", getUserAccessToken("test@test.com"))
                        .content(testData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("User email updated successfully")));
    }

    @Test
    @Order(4)
    void updatePassword_200() throws Exception {
        String testData = "{\"email\":\"test@test.com\", \"password\":\"SecurePassword1\"}";
        mockMvc.perform(patch("/api/user/updatePassword")
                        .header("Authorization", getUserAccessToken("test@test.com"))
                        .content(testData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("User password updated successfully")));
    }

    @Test
    @Order(5)
    void findUserInAgeGroup_200() throws Exception {
        mockMvc.perform(get("/api/user/findUserInAgeGroup?startAge=10&endAge=50")
                        .header("Authorization", getUserAccessToken("test@test.com")))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("User list fetched successfully")));
    }

    @Test
    @Order(6)
    void delete_200() throws Exception {
        mockMvc.perform(delete("/api/user/delete?email=test@test.com")
                        .header("Authorization", getUserAccessToken("test@test.com")))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("User deleted successfully")));
    }
}