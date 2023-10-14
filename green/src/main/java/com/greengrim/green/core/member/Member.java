package com.greengrim.green.core.member;

import com.greengrim.green.common.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    private int point = 0;

    private String introduction;

    private String profileImgUrl;

    private Boolean status = true;

    private Integer reportCnt = 0;

    @NotNull
    private String refreshToken;

    private String deviceToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
