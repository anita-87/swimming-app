package com.anarodriguez.licenses.services;

import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Flux<Profile> listProfilesByLicenceType(LicenceType licenceType) {
        return profileRepository.findAllByLicences(licenceType);
    }
}
