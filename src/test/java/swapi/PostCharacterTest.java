package swapi;

import base.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostCharacterTest extends BaseTest {
    @Test
    public void postPerson() {
        given()
                .spec(reqSpec)
                .when()
                .post(BASE_URL + STARSHIPS + "/" + "100")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
