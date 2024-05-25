package com.pp.api.repository.custom;

import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.entity.QOauthFrameworkAuthorization;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pp.api.entity.QOauthFrameworkAuthorization.oauthFrameworkAuthorization;

public class CustomOauthFrameworkAuthorizationRepositoryImpl extends QuerydslRepositorySupport
        implements CustomOauthFrameworkAuthorizationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomOauthFrameworkAuthorizationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
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

    @Transactional
    @Override
    public void deleteByUserId(Long userId) {
        delete(oauthFrameworkAuthorization)
                .where(oauthFrameworkAuthorization.user.id.eq(userId))
                .execute();
    }

}
