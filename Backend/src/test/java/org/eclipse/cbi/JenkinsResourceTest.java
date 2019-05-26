package org.eclipse.cbi;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class JenkinsResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/jenkins")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}