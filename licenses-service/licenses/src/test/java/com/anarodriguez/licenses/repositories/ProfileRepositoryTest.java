package com.anarodriguez.licenses.repositories;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class ProfileRepositoryTest {

    @Autowired
    ProfileRepository profileRepository;

    @Test
    void shouldSaveSingleProfile() {
        Publisher<Profile> savedProfile = profileRepository.save(ProfileRepositoryTest.getTestProfile());

        StepVerifier
                .create(savedProfile)
                .consumeNextWith(p -> {
                    assertEquals(p.getDni(), "71224455A");
                    assertEquals(p.getFirstName(), "Ana");
                    assertEquals(p.getLastName(), "Rodriguez Fernandez");
                    assertEquals(p.getGender(), Gender.FEMALE);
                    assertEquals(p.getDateOfBirth(), LocalDate.parse("1989-01-01"));
                    assertEquals(p.getCountryOfBirth(), "España");
                    assertEquals(p.getPlaceOfBirth(), "León");
                    assertEquals(p.getEmail(), "some@email.com");
                    assertEquals(p.getPostalCode(), "24901");
                    assertEquals(p.getPhone(), "685028120");
                    assertEquals(p.getLicences(), new ArrayList<>(List.of(LicenceType.MASTER)));

                })
                .verifyComplete();

    }

    public static Profile getTestProfile() {
        return Profile.builder()
                .dni("71224455A")
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
                .licences(new ArrayList<>(List.of(LicenceType.MASTER)))
                .build();
    }
}