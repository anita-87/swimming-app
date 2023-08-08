package com.anarodriguez.licenses.components;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.mappers.LicenceCsvMapper;
import com.anarodriguez.licenses.models.Profile;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class LoadDataCommandLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(LoadDataCommandLineRunner.class);

    @Value("${csv.location}")
    private String csvFile;
    @Override
    public void run(String... args) throws Exception {
        logger.debug("Loading profiles from external path");
        MappingIterator<LicenceCsvMapper> it = readCsvFile(csvFile);
        if (it == null) {
            logger.warn("CSV file '"+ csvFile + "' not found. No profiles will be loaded.");
            return;
        }
        List<Profile> profiles = getProfiles(it);
        profiles.forEach(p -> logger.info(p.toString()));
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
        List<Profile> profiles = new ArrayList<>();
        while (mappingIterator.hasNext()) {
            LicenceCsvMapper p = mappingIterator.next();
            List<LicenceType> licences = new ArrayList<>();
            licences.add(LicenceType.fromString(p.getPosition()));
            profiles.add(Profile.builder()
                    .firstName(p.getFirstName())
                    .lastName(p.getLastName())
                    .gender(Gender.fromString(p.getSex()))
                    .dateOfBirth(LocalDate.parse(p.getDateOfBirth()))
                    .placeOfBirth(p.getPlaceOfBirth())
                    .countryOfBirth(p.getCountryOfBirth())
                    .phone(p.getPhone())
                    .email(p.getEmail())
                    .address(p.getAddress())
                    .postalCode(p.getPostalCode())
                    .licences(licences)
                    .build());
        }
        return profiles;
    }
}
