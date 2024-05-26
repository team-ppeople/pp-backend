package com.pp.api.repository.custom;

import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.QOauthUserToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pp.api.entity.QOauthUser.oauthUser;
import static com.pp.api.entity.QOauthUserToken.oauthUserToken;

public class CustomOauthUserTokenRepositoryImpl extends QuerydslRepositorySupport
        implements CustomOauthUserTokenRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomOauthUserTokenRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QOauthUserToken.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OauthUserToken> findByUserId(Long userId) {
        OauthUserToken entity = from(oauthUserToken)
                .innerJoin(oauthUserToken.oauthUser, oauthUser)
                .fetchJoin()
                .where(oauthUser.user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(entity);
    }

}
