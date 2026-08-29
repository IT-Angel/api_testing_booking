package booking;

import auth.AuthClient;
import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateBookingPutTest extends BaseTest {

    private final BookingSteps bookingSteps = new BookingSteps();
    private final AuthClient authClient = new AuthClient();

    @Test
    void shouldUpdateBookingWithValidData() {
        Response createResponse = bookingSteps.createBooking();

        String token = authClient.createToken();

        int bookingId = createResponse.jsonPath()
                .getInt("bookingid");

        String updateBody = """
                {
                  "firstname": "UpdatedName",
                  "lastname": "UpdatedLastName",
                  "totalprice": 456,
                  "depositpaid": false,
                  "bookingdates": {
                    "checkin": "2026-10-01",
                    "checkout": "2026-10-05"
                  },
                  "additionalneeds": "Lunch"
                }
                """;

        Response r = given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)
                .body(updateBody)
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("UpdatedName"))
                .body("lastname", equalTo("UpdatedLastName"))
                .body("totalprice", equalTo(456))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-10-01"))
                .body("bookingdates.checkout", equalTo("2026-10-05"))
                .extract()
                .response();

        System.out.println(r.getBody().asString());
    }

    @Test
    void shouldReturn403WhenTokenIsMissing() {
        Response createResponse = bookingSteps.createBooking();

        int bookingId = createResponse.jsonPath()
                .getInt("bookingid");

        String updateBody = """
                {
                  "firstname": "UpdatedName",
                  "lastname": "UpdatedLastName",
                  "totalprice": 456,
                  "depositpaid": false,
                  "bookingdates": {
                    "checkin": "2026-10-01",
                    "checkout": "2026-10-05"
                  },
                  "additionalneeds": "Lunch"
                }
                """;

        given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", null)
                .body(updateBody)
                .put("/booking/" + bookingId)
                .then()
                .statusCode(403);

    }

    @Test
    void shouldReturn400WhenJsonSyntaxIsInvalid() {
        Response createResponse = bookingSteps.createBooking();

        String token = authClient.createToken();

        int bookingId = createResponse.jsonPath()
                .getInt("bookingid");

        String invalidJson = """
                {
                  "firstname": ,
                  "lastname": "UpdatedLastName",
                  "totalprice": "abc",
                  "depositpaid": false,
                  "bookingdates": {
                    "checkin": "2026-10-01",
                    "checkout": "2026-10-05"
                  },
                  "additionalneeds": "Lunch"
                }
                """;

        given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token", token)
                .body(invalidJson)
                .put("/booking/" + bookingId)
                .then()
                .statusCode(400);

    }


}



