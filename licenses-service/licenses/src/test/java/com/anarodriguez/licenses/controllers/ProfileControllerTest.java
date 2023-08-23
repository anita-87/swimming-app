package com.anarodriguez.licenses.controllers;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProfileController.class)
@AutoConfigureWebTestClient
class ProfileControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    ProfileService profileService;

    @Test
    void testListSwimmingProfiles() {
        Profile swimProfile = getProfile("71224455A", LicenceType.SWIMMING);

        given(profileService.listProfilesByLicenceType(LicenceType.SWIMMING)).willReturn(Flux.just(swimProfile));

        webTestClient.get()
                .uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "swim")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$[0]['dni']").isEqualTo(swimProfile.getDni());
    }

    @Test
    void testListMasterProfiles() {
        Profile masterProfile = getProfile("71224455A", LicenceType.MASTER);
        Profile masterAndSwimProfile = getProfile("71246975B", LicenceType.MASTER, LicenceType.SWIMMING);

        given(profileService.listProfilesByLicenceType(LicenceType.MASTER)).willReturn(Flux.just(masterProfile, masterAndSwimProfile));

        webTestClient.get()
                .uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "master")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(2)
                .jsonPath("$[0]['dni']").isEqualTo(masterProfile.getDni())
                .jsonPath("$[1]['dni']").isEqualTo(masterAndSwimProfile.getDni());
    }

    @Test
    void testCoachProfiles() {
        Profile coachProfile = getProfile("71224455A", LicenceType.COACH);

        given(profileService.listProfilesByLicenceType(LicenceType.COACH)).willReturn(Flux.just(coachProfile));

        webTestClient.get()
                .uri(ProfileController.PROFILE_BY_LICENCE_TYPE, "coach")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$[0]['dni']").isEqualTo(coachProfile.getDni())
        ;
    }

    private Profile getProfile(String dni, LicenceType... licenceType) {
        return Profile.builder()
                .dni(dni)
                .firstName("Ana")
                .lastName("Rodriguez Fernandez")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.parse("1989-01-01"))
                .countryOfBirth("España")
                .placeOfBirth("León")
                .email("some@email.com")
                .address("Some Random Address")
                .postalCode("24901")
                .phone("685028120")
                .licences(new ArrayList<>(List.of(licenceType)))
                .build();
    }
}