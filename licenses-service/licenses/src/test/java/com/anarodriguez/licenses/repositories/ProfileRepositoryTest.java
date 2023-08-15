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
import reactor.test.StepVerifier;

import java.util.List;

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
        Flux<Profile> coachProfiles = profileRepository.findAllByLicences(LicenceType.COACH);

        StepVerifier
                .create(coachProfiles)
                .expectNextCount(2)
                .verifyComplete();
    }

}