package com.anarodriguez.licenses.repositories;

import com.anarodriguez.licenses.bootstrap.BootstrapDataTest;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileRepositoryTest {

    @Autowired
    ProfileRepository profileRepository;

    @BeforeAll
    void setUp() {
        List<Profile> profiles = BootstrapDataTest.setupProfiles();
        profileRepository.saveAll(profiles)
                .subscribe();
    }

    @Test
    void shouldReturnFourProfilesAfterSetUp() {
        Flux<Profile> savedProfiles = profileRepository.findAll();

        StepVerifier
                .create(savedProfiles)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void shouldReturnTwoCoachProfiles() {
        Flux<Profile> coachProfiles = profileRepository.findAllByLicencesOrderByFirstName(LicenceType.COACH);

        StepVerifier
                .create(coachProfiles)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldReturnTheProfileByDNI() {
        Mono<Profile> profile = profileRepository.findByDni("71224455A");

        StepVerifier
                .create(profile)
                .assertNext(retrivedProfile -> {
                    assertAll(
                            "profileByDni",
                            () -> assertEquals("71224455A", retrivedProfile.getDni()),
                            () -> assertEquals("Ana", retrivedProfile.getFirstName()),
                            () -> assertEquals(1, retrivedProfile.getLicences().size())
                    );
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnNoProfileWhenDniIsNotFound() {
        Mono<Profile> profile = profileRepository.findByDni("71224455Z");

        StepVerifier
                .create(profile)
                .expectNextCount(0)
                .verifyComplete();
    }
}