package com.anarodriguez.licenses.services;

import com.anarodriguez.licenses.models.Profile;

import java.util.List;

public interface ProfileService {

    List<Profile> saveOrUpdateAllProfiles(List<Profile> profiles);
}
