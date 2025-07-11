package com.congthanh.commonservice;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.Map;

public class AbstractControllerIT {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    protected String authServerUrl;

    @LocalServerPort
    private int port;

    protected RequestSpecification getRequestSpecification() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        return new RequestSpecBuilder()
                .setPort(port)
                .addHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    protected String getAccessToken(String username, String password) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", username,
                        "password", password,
                        "scope", "openid",
                        "grant_type", "password",
                        "client_id", "quarkus-service",
                        "client_secret", "secret"
                ))
                .post(authServerUrl + "/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }

    protected RequestSpecification givenLoggedInAsAdmin() {
        return given(getRequestSpecification())
                .auth().oauth2(getAccessToken("admin", "admin"));
    }
}
