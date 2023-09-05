package com.anarodriguez.licenses.repositories;

import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

    Flux<Profile> findAllByLicencesOrderByFirstName(LicenceType licenceType);
}
