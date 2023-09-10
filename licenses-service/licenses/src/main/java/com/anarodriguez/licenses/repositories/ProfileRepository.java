package com.anarodriguez.licenses.repositories;

import com.anarodriguez.licenses.enums.LicenceType;
import com.anarodriguez.licenses.models.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

    Flux<Profile> findAllByLicencesOrderByFirstName(LicenceType licenceType);
    Mono<Profile> findByDni(String dni);
}
