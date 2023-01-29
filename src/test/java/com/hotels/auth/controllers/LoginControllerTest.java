package com.hotels.auth.controllers;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.ws.rs.core.Response;

import com.hotels.auth.requests.LoginRequest;
import com.hotels.auth.services.AuthServiceImpl;
import com.hotels.users.enumerations.UserType;
import com.hotels.users.models.User;
import com.hotels.users.services.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@QuarkusTest
class LoginControllerTest {
    @InjectMock
    UserService userService;

    User user;
    private final String email = "test2@hotels.test";
    private final String password = "password";

    LoginRequest request;

    @BeforeEach
    void setUp(){
        user = User.builder()
            .id(1L)
            .clientId(1L)
            .active(true)
            .email(email)
            .name("Test User")
            .type(UserType.BUSINESS_OWNER)
            .password(BcryptUtil.bcryptHash(password))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
    }

    @Test
    void login() {
        Mockito.when(userService.findByEmailAddress(email)).thenReturn(Optional.of(user));
        given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body(containsString(AuthServiceImpl.LOGIN_SUCCESSFUL_MESSAGE));
    }

    @Test
    void loginInvalidCredentials() {
        Mockito.when(userService.findByEmailAddress(email)).thenReturn(Optional.of(user));
        request.setPassword("wrongpassword");

        given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body(containsString(AuthServiceImpl.INVALID_CREDENTIALS_MESSAGE));
    }

    @Test
    void loginAccountBlocked() {
        user = User.builder()
            .id(1L)
            .clientId(1L)
            .active(false)
            .email(email)
            .name("Test User")
            .type(UserType.BUSINESS_OWNER)
            .password(BcryptUtil.bcryptHash(password))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Mockito.when(userService.findByEmailAddress(email)).thenReturn(Optional.of(user));

        given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode())
            .body(containsString(AuthServiceImpl.ACCOUNT_BLOCKED_MESSAGE));
    }

    @Test
    void loginNoUserWithGivenEmail() {
        Mockito.when(userService.findByEmailAddress(email)).thenReturn(Optional.empty());
        given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body(containsString(AuthServiceImpl.INVALID_CREDENTIALS_MESSAGE));
    }
}
