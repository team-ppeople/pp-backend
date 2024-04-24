package com.pp.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Post post;

    @ManyToOne(fetch = LAZY, optional = false)
    private UploadFile uploadFile;

    @Builder
    private PostImage(
            Post post,
            UploadFile uploadFile
    ) {
        this.post = post;
        this.uploadFile = uploadFile;

        this.post.addImage(this);
    }

}
