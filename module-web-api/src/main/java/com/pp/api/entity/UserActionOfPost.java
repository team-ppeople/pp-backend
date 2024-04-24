package com.pp.api.entity;

import com.pp.api.entity.enums.UserAction;
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
public class UserActionOfPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Post post;

    @ManyToOne(fetch = LAZY, optional = false)
    private User user;

    @Column(nullable = false, length = 50)
    @Enumerated(value = STRING)
    private UserAction userAction;

    @Builder
    private UserActionOfPost(
            Post post,
            User user,
            UserAction userAction
    ) {
        this.post = post;
        this.user = user;
        this.userAction = userAction;
    }

}
