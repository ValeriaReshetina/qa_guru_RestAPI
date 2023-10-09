package guru.qa.classworkTests;

import guru.qa.models.login.LoginBodyLombokModel;
import guru.qa.models.login.LoginResponseLombokModel;
import guru.qa.models.registration.MissingPasswordResponseLombokModel;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static guru.qa.helpers.CustomAllureListener.withCustomTemplates;
import static guru.qa.specs.LoginSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class ClassworkLoginExtendedTests {
    @Test
    void successfulLoginBadPracticeTest() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginWithLombokTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");
        LoginResponseLombokModel response = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }
    @Test
    void successfulLoginWithAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make login request", () ->
                given()
                        .filter(new AllureRestAssured())
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModel.class));
        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }
    @Test
    void successfulLoginWithCustomAllureTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");
        LoginResponseLombokModel response = step("Make login request", () ->
                given()
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModel.class));
        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successfulLoginWithSpecsTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make login request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Verify response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void missingPasswordTest() {
        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");

        MissingPasswordResponseLombokModel response = step("Make login request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(missingPasswordResponseSpec)
                        .extract().as(MissingPasswordResponseLombokModel.class));

        step("Verify response", () ->
                assertEquals("Missing password", response.getError()));
    }
}
