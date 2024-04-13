package com.pp.api.repository;

import com.pp.api.entity.OauthUserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthUserTokensRepository extends JpaRepository<OauthUserTokens, Long> {

}
