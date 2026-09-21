package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.RestAssured;
import org.opentest4j.TestAbortedException;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public final class CucumberStepSupport {

    private CucumberStepSupport() {
    }

    public static Long requireId(Long id, String message) {
        if (id == null) {
            skip(message);
        }
        return id;
    }

    public static void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            skip(message);
        }
    }

    public static void skip(String message) {
        throw new TestAbortedException(message);
    }

    public static void configureRestAssured(TestProperties testProperties) {
        RestAssured.baseURI = testProperties.getBaseUrl();
    }

    public static void requireBackendReachable(TestProperties testProperties) {
        assertThat(testProperties.getBaseUrl()).isNotBlank();
        URI baseUri = URI.create(testProperties.getBaseUrl());
        int port = baseUri.getPort() == -1 ? 80 : baseUri.getPort();

        try (Socket ignored = new Socket(baseUri.getHost(), port)) {
            // Reachable.
        } catch (IOException exception) {
            skip("TMS backend is not reachable at " + testProperties.getBaseUrl() + ".");
        }
    }
}
