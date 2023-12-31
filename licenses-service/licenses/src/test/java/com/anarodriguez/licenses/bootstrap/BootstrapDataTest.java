package com.anarodriguez.licenses.bootstrap;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@org.springframework.context.annotation.Profile("test")
public class BootstrapDataTest implements CommandLineRunner {

    private final ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Saving profiles for testing");
        profileRepository.deleteAll()
                .thenMany(profileRepository.saveAll(BootstrapDataTest.setupProfiles()))
                .subscribe();

    }

    public static List<Profile> setupProfiles() {
        List<Profile> profiles = new ArrayList<>();

        profiles.add(Profile.builder()
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
                .build());

        profiles.add(Profile.builder()
                .dni("72236335B")
                .firstName("Carlos")
                .lastName("Perez Alvarez")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.parse("2004-02-02"))
                .countryOfBirth("España")
                .placeOfBirth("León")
                .email("mail@email.com")
                .address("My custom address")
                .postalCode("24001")
                .phone("624218241")
                .licences(new ArrayList<>(List.of(LicenceType.SWIMMING)))
                .build());

        profiles.add(Profile.builder()
                .dni("73525389A")
                .firstName("Luis")
                .lastName("Garcia Garcia")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.parse("1999-03-03"))
                .countryOfBirth("España")
                .placeOfBirth("León")
                .email("mail2@email.com")
                .address("My Address")
                .postalCode("24502")
                .phone("678027320")
                .licences(new ArrayList<>(List.of(LicenceType.MASTER, LicenceType.SWIMMING, LicenceType.COACH)))
                .build());

        profiles.add(Profile.builder()
                .dni("75467842A")
                .firstName("Laura")
                .lastName("Fernandez Gomez")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.parse("1971-05-01"))
                .countryOfBirth("España")
                .placeOfBirth("León")
                .email("mail@email.com")
                .address("Random Address")
                .postalCode("24005")
                .phone("692638120")
                .licences(new ArrayList<>(List.of(LicenceType.COACH)))
                .build());

        return profiles;
    }
}
