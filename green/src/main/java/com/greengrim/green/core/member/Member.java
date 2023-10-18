package com.greengrim.green.core.member;

import com.greengrim.green.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String nickName;

    @NotNull
    private int point;

    private String introduction;

    private String profileImgUrl;

    @NotNull
    private boolean status;

    @NotNull
    private Integer reportCnt;

    @NotNull
    private String refreshToken;

    @NotNull
    private String deviceToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
