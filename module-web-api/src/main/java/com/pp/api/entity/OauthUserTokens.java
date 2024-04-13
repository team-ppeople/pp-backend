package com.pp.api.entity;

import com.pp.api.entity.enums.OauthUserClient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class OauthUserTokens {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Enumerated(value = STRING)
    private OauthUserClient client;

    @Column(nullable = false, length = 1500)
    private String accessToken;

    @Column(nullable = false)
    private Integer expiresIn;

    @Column(nullable = false, length = 1500)
    private String refreshToken;

    @ManyToOne(fetch = LAZY, optional = false)
    private OauthUsers oauthUser;

    @Builder
    private OauthUserTokens(
            OauthUserClient client,
            String accessToken,
            Integer expiresIn,
            String refreshToken,
            OauthUsers oauthUser
    ) {
        this.client = client;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.oauthUser = oauthUser;
    }

}
