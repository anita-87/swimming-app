package com.anarodriguez.licenses.controllers;

import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    public static final String PROFILE_PATH = "/profiles";
    public static final String PROFILE_BY_LICENCE_TYPE = PROFILE_PATH + "/type/{licenceType}";
    public static final String PROFILE_BY_DNI = PROFILE_PATH + "/{dni}";

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;

    @GetMapping(PROFILE_BY_LICENCE_TYPE)
    ResponseEntity<Flux<Profile>> listProfilesByLicenceType(@PathVariable("licenceType") String licenceType) {
        logger.debug("Getting profiles for licence type " + licenceType);
        return ResponseEntity
                .ok()
                .body(profileService.listProfilesByLicenceType(LicenceType.fromString(licenceType)));
    }

    @GetMapping(PROFILE_BY_DNI)
    Mono<ResponseEntity<Profile>> getProfileByDni(@PathVariable("dni") String dni) {
        logger.debug("Getting profile for dni " + dni);
        Mono<Profile> profile = profileService.getProfile(dni);
        return profile.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
