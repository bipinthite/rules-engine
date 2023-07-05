package com.example.rules.controller;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RulesControllerIT {

  @SuppressWarnings("unused")
  @LocalServerPort private Integer port;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void fire_nonExistentKB_returnsNotFoundStatusCode() {
    final Response response =
        given()
            .header("Content-type", "application/json")
            .header("kb", "UnknownKB")
            .and()
            .body("{\"BMI\":17}")
            .when()
            .post("/api/rules/v1/fire")
            .then()
            .extract()
            .response();
    Assertions.assertEquals(404, response.statusCode());
  }
}
