package com.pp.api.repository;

import com.pp.api.entity.ProfileImages;
import com.pp.api.repository.custom.CustomProfileImagesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImagesRepository extends JpaRepository<ProfileImages, Long>, CustomProfileImagesRepository {
}
