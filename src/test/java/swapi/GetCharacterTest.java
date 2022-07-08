package swapi;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCharacterTest extends BaseTest {

    @Test
    public void readCharacters() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + PEOPLE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> resultsName = json.getList("results.name");
        assertThat(resultsName).hasSize(10);
        assertThat(resultsName.get(3)).isEqualTo("Darth Vader");
    }

    @Test
    public void readOneCharacter() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + PEOPLE + "/44")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Darth Maul", json.get("name"));
        assertEquals("175", json.get("height"));
        assertEquals("https://swapi.py4e.com/api/planets/36/", json.get("homeworld"));
        assertEquals("yellow", json.get("eye_color"));

    }

    @Test
    public void readOneCharacterWithPathVariable() {

        Response response = given()
                .spec(reqSpec)
                .pathParam("userId", 36)
                .when()
                .get(BASE_URL + PEOPLE + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Jar Jar Binks", json.get("name"));
        assertEquals("male", json.get("gender"));
        assertEquals("https://swapi.py4e.com/api/planets/8/", json.get("homeworld"));
        assertEquals("https://swapi.py4e.com/api/people/36/", json.get("url"));
    }

    @Test
    public void readCharacterWithQueryParams() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("search", "Watto")
                .when()
                .get(BASE_URL + PEOPLE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getList("results.name")).contains("Watto");
    }
}
