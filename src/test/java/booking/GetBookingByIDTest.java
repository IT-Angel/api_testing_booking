package booking;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class GetBookingByIDTest extends BaseTest {

    @Test
    void getBookingById_returnsExpectedFields() {
        int bookingId = given()
                .when()
                .get("/booking") // вернёт JSON c id'ами
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .path("[0].bookingid"); // первый id
        System.out.println("bookingId=" + bookingId);

//        Response r = given()
//                .when()
//                .get("/booking/" + bookingId);
//
//        System.out.println(r.getStatusCode());
//        System.out.println(r.getContentType());
//        System.out.println(r.getBody().asString());


        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                //.body("bookingid", equalTo(bookingId))
                .body("firstname", instanceOf(String.class))
                .body("lastname", instanceOf(String.class))
                .body("totalprice", instanceOf(Number.class))
                .body("depositpaid", instanceOf(Boolean.class))
                .body("bookingdates", instanceOf(java.util.Map.class))
                .body("bookingdates.checkin", matchesPattern("\\d{4}-\\d{2}-\\d{2}"))
                .body("bookingdates.checkout", notNullValue());

    }
}



