package com.pp.api.repository;

import com.pp.api.entity.ProfileImage;
import com.pp.api.repository.custom.CustomProfileImageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long>, CustomProfileImageRepository {
}
