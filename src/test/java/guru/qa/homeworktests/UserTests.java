package guru.qa.homeworktests;

import guru.qa.models.user.UserListResponseModel;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static guru.qa.specs.UserSpec.userRequestSpec;
import static guru.qa.specs.UserSpec.userResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends TestBase {

    @Test
    void getUserListTest() {
        UserListResponseModel response = step("GET-request for user list", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UserListResponseModel.class));

        step("Verify response", () -> {
            assertThat(response.getTotal()).isEqualTo(12);
            assertThat(response.getData()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(user -> user.getId() == 10)
                    .findFirst()
                    .get()
                    .getEmail()).isEqualTo("byron.fields@reqres.in");
        });
    }
}
