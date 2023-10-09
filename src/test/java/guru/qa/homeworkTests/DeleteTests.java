package guru.qa.homeworkTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static guru.qa.specs.DeleteSpec.deleteResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DeleteTests extends TestBase {

    @DisplayName("Successful user deletion")
    @Test
    void userDeletionTest() {
        step("DELETE-request for user deletion", () -> {
            given()
                    .delete("/users/2")
                    .then()
                    .log().body()
                    .spec(deleteResponseSpec);
        });
    }
}
