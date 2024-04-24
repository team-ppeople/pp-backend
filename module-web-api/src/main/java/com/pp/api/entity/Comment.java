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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY, optional = false)
    public Post post;

    @OneToMany(fetch = LAZY, mappedBy = "comment")
    private List<ReportedComment> reports;

    @Builder
    private Comment(
            String content,
            Post post
    ) {
        this.content = content;
        this.post = post;
        this.reports = new ArrayList<>();

        this.post.addComment(this);
    }

    public void addReport(ReportedComment reportedComment) {
        this.reports.add(reportedComment);
    }

}
