package br.com.ntconsult.core;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    @BeforeAll
    static void globalSetup() {
        RestAssured.baseURI  = "https://dog.ceo";
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @BeforeEach
    void setupPerTest() {
        RestAssured.filters(new AllureRestAssured());
    }
}