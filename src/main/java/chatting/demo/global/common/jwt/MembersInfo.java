package chatting.demo.global.common.jwt;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MembersInfo {

    private Long id;
    private String username;
}
