package com.pp.api.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(length = 350, unique = true)
    private String email;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<ProfileImages> profileImages;

    @OneToMany(fetch = LAZY, mappedBy = "creator")
    private List<Posts> posts;

    @Builder
    private Users(
            String nickname,
            String email
    ) {
        this.nickname = nickname;
        this.email = email;
        this.profileImages = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void addProfileImage(ProfileImages profileImage) {
        this.profileImages.add(profileImage);
    }

    public void addPost(Posts post) {
        this.posts.add(post);
    }

}
