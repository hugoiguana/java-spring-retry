package hugo.examples.spring_retry.controller;

import hugo.examples.spring_retry.feing.TestExternalFeing;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import java.lang.module.FindException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    private Integer port;

    @MockBean
    TestExternalFeing testExternalFeing;

    UUID id;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        id = UUID.randomUUID();
    }

    @Test
    void test1_should_retry_when_the_exception_is_not_NullPointerException() {

        doThrow(FindException.class).when(testExternalFeing).getExternalTest(id);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/tests/test1/{id}", id)
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        verify(testExternalFeing, times(3)).getExternalTest(id);
    }

    @Test
    void test1_should_not_retry_when_the_exception_is_specified_on_noRetryFor_property_of_Retryable() {

        doThrow(NullPointerException.class).when(testExternalFeing).getExternalTest(id);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/tests/test1/{id}", id)
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        verify(testExternalFeing, times(1)).getExternalTest(id);
    }

    @Test
    void test1_should_not_retry_whe_any_exception_not_occur() {

        doReturn("test123").when(testExternalFeing).getExternalTest(id);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/tests/test1/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", notNullValue())
                .body("name", equalTo("test123"));

        verify(testExternalFeing, times(1)).getExternalTest(id);
    }

}