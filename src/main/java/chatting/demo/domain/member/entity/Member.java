package chatting.demo.domain.member.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import chatting.demo.domain.like.entity.Likes;
import chatting.demo.global.common.basetime.BaseTimeEntity;
import chatting.demo.domain.member.entity.enums.Role;
import chatting.demo.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String profileUrl;

    @Column(length = 30, unique=true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    private String refreshToken;

    @Builder
    public Member(Long id, String password, String profileUrl, String email, Role role, String nickname, List<Post> postList, List<Likes> likes, String refreshToken) {
        this.id = id;
        this.password = password;
        this.profileUrl = profileUrl;
        this.email = email;
        this.role = role;
        this.nickname = nickname;
        this.postList = postList;
        this.likes = likes;
        this.refreshToken = refreshToken;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public void setUserPassword(String pw) {
        this.password = pw;
    }

    public void setProfileImageUrl(String upload) {
        this.profileUrl = upload;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

