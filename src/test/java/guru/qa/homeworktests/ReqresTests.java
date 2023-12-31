package guru.qa.homeworktests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests extends TestBase {

    @DisplayName("Successful creation of user")
    @Test
    void userCreationTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"Valeria Reshetina\", \"job\": \"QA\" }")
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @DisplayName("Successful user deletion")
    @Test
    void userDeletionTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"Valeria Reshetina\", \"job\": \"QA\" }")
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @DisplayName("Successful user registration")
    @Test
    void userRegistrationTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }")
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @DisplayName("Failed attempt to register user")
    @Test
    void negativeRegisterUserTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\" }")
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @DisplayName("Successful retrieving a list of users")
    @Test
    void getUsersListTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}
