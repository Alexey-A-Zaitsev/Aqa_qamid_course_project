package ru.netology.web.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class APIHelper {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/api")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static void payWithCard(int expectedStatusCode, String expectedTransactionStatus, DataHelper.Card card) {
        given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post("/v1/pay")
                .then()
                .statusCode(expectedStatusCode)
                .body("status", equalTo(expectedTransactionStatus));

    }

    public static void creditPay(int expectedStatusCode, String expectedTransactionStatus) {
        given()
                .spec(requestSpec)
                .body(DataHelper.Card.class)
                .when()
                .post("/v1/credit")
                .then()
                .statusCode(expectedStatusCode)
                .body("status", equalTo(expectedTransactionStatus));

    }

/*    // Оплата одобренной картой
    public static void shouldPayWithApprovedCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/pay")
                .then()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    public static void shouldPayWithDeclinedCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/pay")
                .then()
                .statusCode(200)
                .body("status", equalTo("DECLINED"));
    }

    public static void shouldPayWithRandomCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/pay")
                .then()
                .statusCode(500);
    }

    public static void shouldCreditWithApprovedCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/credit")
                .then()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    public static void shouldCreditWithDeclinedCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/credit")
                .then()
                .statusCode(200)
                .body("status", equalTo("DECLINED"));
    }

    public static void shouldCreditWithRandomCard(DataHelper.Card payInfo) {
        String requestBody = DataHelper.createJSON(payInfo);
        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/v1/credit")
                .then()
                .statusCode(500);
    }
*/


}
