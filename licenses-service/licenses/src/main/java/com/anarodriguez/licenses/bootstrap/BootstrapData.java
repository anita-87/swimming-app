package com.anarodriguez.licenses.bootstrap;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.mappers.LicenceCsvMapper;
import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.repositories.ProfileRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
@org.springframework.context.annotation.Profile("!test")
public class BootstrapData implements CommandLineRunner {

    @Value("${csv.location}")
    private String csvFile;

    private final ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Loading profiles from external path");
        MappingIterator<LicenceCsvMapper> it = readCsvFile(csvFile);
        if (it == null) {
            log.warn("CSV file '"+ csvFile + "' not found. No profiles will be loaded.");
            return;
        }
        List<Profile> profiles = getProfiles(it);
        log.info("Obtained " + profiles.size() + " profiles after filtering the profiles read from the CSV file.");
        this.profileRepository.saveAll(profiles)
                .subscribe();
    }

    private MappingIterator<LicenceCsvMapper> readCsvFile(String fileName) throws IOException {
        BufferedReader reader  = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        ObjectMapper mapper = new CsvMapper();
        return mapper.readerFor(LicenceCsvMapper.class)
                .with(bootstrapSchema)
                .readValues(reader);
    }

    private List<Profile> getProfiles(MappingIterator<LicenceCsvMapper> mappingIterator) {
        Map<String, Profile> profiles = new HashMap<>();
        int csvElements = 0;
        while (mappingIterator.hasNext()) {
            csvElements++;
            LicenceCsvMapper p = mappingIterator.next();
            Profile profile = buildProfile(p);
            if (profiles.containsKey(p.getDni())) {
                Profile savedProfile = profiles.get(p.getDni());
                savedProfile.getLicences().addAll(profile.getLicences());
                profiles.put(savedProfile.getDni(), savedProfile);
            } else {
                profiles.put(profile.getDni(), profile);
            }
        }
        log.info("Read " + csvElements + " profiles from the CSV file");
        return profiles.values().stream().toList();
    }

    private Profile buildProfile(LicenceCsvMapper csvProfile) {
        List<LicenceType> licences = new ArrayList<>();
        licences.add(LicenceType.fromString(csvProfile.getPosition()));
        return Profile.builder()
                .dni(csvProfile.getDni())
                .firstName(csvProfile.getFirstName())
                .lastName(csvProfile.getLastName())
                .gender(Gender.fromString(csvProfile.getSex()))
                .dateOfBirth(LocalDate.parse(csvProfile.getDateOfBirth()))
                .placeOfBirth(csvProfile.getPlaceOfBirth())
                .countryOfBirth(csvProfile.getCountryOfBirth())
                .phone(csvProfile.getPhone())
                .email(csvProfile.getEmail())
                .address(csvProfile.getAddress())
                .postalCode(csvProfile.getPostalCode())
                .licences(licences)
                .build();
    }

}
