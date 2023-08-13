package com.anarodriguez.licenses.services;

import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import reactor.core.publisher.Flux;

public interface ProfileService {
    Flux<Profile> listProfilesByLicenceType(LicenceType licenceType);
}
