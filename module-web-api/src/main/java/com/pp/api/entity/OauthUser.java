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
public class OauthUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Enumerated(value = STRING)
    private OauthUserClient client;

    @Column(nullable = false, length = 300, unique = true)
    private String clientSubject;

    @ManyToOne(fetch = LAZY, optional = false)
    private User user;

    @Builder
    private OauthUser(
            OauthUserClient client,
            String subject,
            User user) {
        this.client = client;
        this.clientSubject = this.client.parseClientSubject(subject);
        this.user = user;
    }

}
