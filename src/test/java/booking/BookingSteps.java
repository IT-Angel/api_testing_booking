package booking;

import base.BaseTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class BookingSteps extends BaseTest {

    public Response createBooking() {
        String requestBody = """
        {
          "firstname": "John",
          "lastname": "Doe",
          "totalprice": 123,
          "depositpaid": true,
          "bookingdates": {
            "checkin": "2026-09-01",
            "checkout": "2026-09-05"
          },
          "additionalneeds": "Breakfast"
        }
        """;

        return given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

    }

}
