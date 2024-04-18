package com.pp.api.repository.custom;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.entity.QOauthFrameworkAuthorization;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pp.api.entity.QOauthFrameworkAuthorization.oauthFrameworkAuthorization;

public class CustomOauth2FrameworkAuthorizationRepositoryImpl extends QuerydslRepositorySupport
        implements CustomOauth2FrameworkAuthorizationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomOauth2FrameworkAuthorizationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QOauthFrameworkAuthorization.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OauthFrameworkAuthorization> findByAnyToken(String token) {
        OauthFrameworkAuthorization entity = from(oauthFrameworkAuthorization)
                .where(
                        oauthFrameworkAuthorization.state.eq(token)
                                .or(oauthFrameworkAuthorization.accessTokenValue.eq(token))
                                .or(oauthFrameworkAuthorization.refreshTokenValue.eq(token))
                                .or(oauthFrameworkAuthorization.oidcIdTokenValue.eq(token))
                                .or(oauthFrameworkAuthorization.userCodeValue.eq(token))
                                .or(oauthFrameworkAuthorization.deviceCodeValue.eq(token))
                )
                .fetchOne();

        return Optional.ofNullable(entity);
    }
}
