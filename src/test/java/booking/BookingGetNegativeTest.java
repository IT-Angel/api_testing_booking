package booking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class BookingGetNegativeTest {
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    void getBookingById_nonExisting_basicChecks() {
         given()
                .when()
                .get("/booking/99999999")
                .then()
                .statusCode(404)
                .body(containsString("Not Found"));


        Response r = given()
                .when()
                .get("/booking/" + 99999999);

        System.out.println(r.getStatusCode());
        System.out.println(r.getContentType());
        System.out.println(r.getBody().asString());

    }
}
