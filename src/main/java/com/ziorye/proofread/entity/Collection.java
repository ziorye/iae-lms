package com.ziorye.proofread.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String title;
    String titleTranslation;
    String slug;
    String type;
    String content;
    String video;
    long duration;
    String cover;
    String description;
    boolean published;
    boolean free;
    Double price;
    int viewCount;
    String seoTitle;
    String seoDescription;
    boolean completed;
    boolean proofread;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
