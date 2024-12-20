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

@Table(name = "users")
@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(length = 350)
    private String email;

    @OneToMany(fetch = LAZY, mappedBy = "user")
    private List<ProfileImage> profileImages;

    @OneToMany(fetch = LAZY, mappedBy = "creator")
    private List<Post> posts;

    @Builder
    private User(
            String nickname,
            String email
    ) {
        this.nickname = nickname;
        this.email = email;
        this.profileImages = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void addProfileImage(ProfileImage profileImage) {
        this.profileImages.add(profileImage);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

}
