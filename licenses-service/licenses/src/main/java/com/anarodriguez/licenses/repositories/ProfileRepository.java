package com.anarodriguez.licenses.repositories;

import com.anarodriguez.licenses.models.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

}
