package chatting.demo.domain.post.entity;

import chatting.demo.global.common.basetime.BaseTimeEntity;
import chatting.demo.domain.like.entity.Likes;
import chatting.demo.domain.member.entity.Member;
import chatting.demo.domain.post.entity.enums.Classification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * author : 강민승
 */
@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 150)
    private String content;

    private int views;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Files> uploadFiles;

    @Enumerated(EnumType.STRING)
    private Classification classification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @Builder
    public Post(Long id, String title, String content, int views, List<Files> uploadFiles, Classification classification, Member member, List<Likes> likes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.uploadFiles = uploadFiles;
        this.classification = classification;
        this.member = member;
        this.likes = likes;
    }

    public void setViews(int views){
        this.views = views;
    }


    public void setUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setFeedUpdate(String content) {
        this.content = content;
    }

}

