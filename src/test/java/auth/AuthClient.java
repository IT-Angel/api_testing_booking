package auth;

import base.BaseTest;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseTest {

    public String createToken() {

        String authBody = """
                {
                  "username": "admin",
                  "password": "password123"
                }
                """;
        return given()
                .contentType("application/json")
                .body(authBody)
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

    }
}
