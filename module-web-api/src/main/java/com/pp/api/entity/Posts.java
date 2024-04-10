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
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY, optional = false)
    private Users creator;

    @OneToMany(fetch = LAZY, mappedBy = "post")
    private List<PostImages> images;

    @OneToMany(fetch = LAZY, mappedBy = "post")
    private List<Comments> comments;

    @OneToMany(fetch = LAZY, mappedBy = "post")
    private List<ReportedPosts> reports;

    @Builder
    private Posts(
            String title,
            String content,
            Users creator
    ) {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.images = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.reports = new ArrayList<>();

        this.creator.addPost(this);
    }

    public void addImage(PostImages postImage) {
        this.images.add(postImage);
    }

    public void addComment(Comments comment) {
        this.comments.add(comment);
    }

    public void addReport(ReportedPosts reportedPost) {
        this.reports.add(reportedPost);
    }

}
