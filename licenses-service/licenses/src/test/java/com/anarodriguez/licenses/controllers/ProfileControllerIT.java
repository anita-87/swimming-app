package com.anarodriguez.licenses.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ProfileControllerIT {

    @Autowired
    WebTestClient webTestClient;

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_JSON = "application/json";

    @Test
    void testListSwimmingProfiles() {
        webTestClient
                .get().uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "swim")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    @Test
    void testListMasterProfiles() {
        webTestClient
                .get().uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "master")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    @Test
    void testListCoachProfiles() {
        webTestClient
                .get().uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "coach")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }
}