package com.pp.api.entity;

import com.pp.api.entity.enums.UserActions;
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
public class UserActionsOfPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Posts post;

    @ManyToOne(fetch = LAZY, optional = false)
    private Users user;

    @Column(nullable = false, length = 50)
    @Enumerated(value = STRING)
    private UserActions userAction;

    @Builder
    private UserActionsOfPost(
            Posts post,
            Users user,
            UserActions userAction
    ) {
        this.post = post;
        this.user = user;
        this.userAction = userAction;
    }

}
