package ru.netology.web.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class APIHelper {
    private  static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/api")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    // Оплата одобренной картой
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

}
