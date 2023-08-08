package com.anarodriguez.licenses.services;

import com.anarodriguez.licenses.models.Profile;
import com.anarodriguez.licenses.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public List<Profile> saveOrUpdateAllProfiles(List<Profile> profiles) {
        profileRepository.saveAll(profiles);
        return profiles;
    }
}
