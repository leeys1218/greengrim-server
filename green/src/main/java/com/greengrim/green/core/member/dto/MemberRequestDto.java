package com.greengrim.green.core.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterMemberReq {

        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        private String nickName;
        private String introduction;
        private String profileImgUrl;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginMemberReq {
        @Email(message= "이메일 형식이 아닙니다.")
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckNickNameReq {
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        private String nickName;
    }
}
