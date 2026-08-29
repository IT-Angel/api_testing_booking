package booking;

import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateBookingPostTest extends BaseTest {

    @Test
    void createBooking_basicChecks() {
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

        Response r =
                given()
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .contentType(containsString("application/json"))
                        .body("bookingid", notNullValue())
                        .body("booking.firstname", equalTo("John"))
                        .body("booking.lastname", equalTo("Doe"))
                        .extract()
                        .response();

        int bookingId = r.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        System.out.println(r.getStatusCode());
        System.out.println(r.getContentType());
        System.out.println(r.getBody().asString());

    }
}