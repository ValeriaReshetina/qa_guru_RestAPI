package guru.qa.homeworkTests;

import guru.qa.models.registration.MissingPasswordResponseLombokModel;
import guru.qa.models.registration.RegistrationBodyModel;
import guru.qa.models.registration.RegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.specs.RegistrationSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTests extends TestBase {

    @DisplayName("Successful user registration")
    @Test
    void userRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");

        RegistrationResponseModel response = step("POST-request for user registration", () ->
                given(registrationRequestSpec)
                        .body(registrationData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registrationResponseSpec)
                        .extract().as(RegistrationResponseModel.class));

        step("Verify response", () -> {
            assertThat(response.getId()).isEqualTo(4);
            assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }

    @DisplayName("Failed attempt to register user")
    @Test
    void negativeRegisterUserTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel();
        registrationData.setEmail("eve.holt@reqres.in");

        MissingPasswordResponseLombokModel response = step("POST-request for user registration without password", () ->
                given(registrationRequestSpec)
                        .body(registrationData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(missingPasswordRegistrationSpec)
                        .extract().as(MissingPasswordResponseLombokModel.class));

        step("Verify response", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}
