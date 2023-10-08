package chatting.demo.domain.post.entity;

import chatting.demo.global.common.basetime.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Files extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String files;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
