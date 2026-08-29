package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static final String BASE_URL = "https://restful-booker.herokuapp.com";
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }
}
