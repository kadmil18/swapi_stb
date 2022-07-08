package swapi;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStarshipsTest extends BaseTest {

    @Test
    public void readStarship() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + STARSHIPS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> resultsName = json.getList("results.name");
        assertThat(resultsName).hasSize(10);
        assertThat(resultsName.get(3)).isEqualTo("Death Star");
    }

    @Test
    public void readOneStarship() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + STARSHIPS + "/3")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Star Destroyer", json.get("name"));
        assertEquals("150000000", json.get("cost_in_credits"));
        assertEquals("47,060", json.get("crew"));
        assertEquals("Kuat Drive Yards", json.get("manufacturer"));

    }

    @Test
    public void readOneStarshipWithPathVariable() {

        Response response = given()
                .spec(reqSpec)
                .pathParam("Id", 66)
                .when()
                .get(BASE_URL + STARSHIPS + "/{Id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("arc-170", json.get("name"));
        assertEquals("1.0", json.get("hyperdrive_rating"));
        assertEquals("starfighter", json.get("starship_class"));
        assertEquals("https://swapi.py4e.com/api/starships/66/", json.get("url"));
    }

    @Test
    public void readStarshipWithQueryParams() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("search", "Republic Cruiser")
                .when()
                .get(BASE_URL + STARSHIPS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getList("results.name")).contains("Republic Cruiser");
    }
}