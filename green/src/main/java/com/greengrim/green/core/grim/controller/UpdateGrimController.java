package com.greengrim.green.core.grim.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.core.grim.dto.GrimRequestDto.UpdateTitle;
import com.greengrim.green.core.grim.service.UpdateGrimService;
import com.greengrim.green.core.member.Member;
import com.greengrim.green.core.member.dto.MemberRequestDto.ModifyProfile;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateGrimController {

    private final UpdateGrimService updateGrimService;

    /**
     * [PATCH] 그림 제목 수정
     */
    @Operation(summary = "그림 제목 수정")
    @PatchMapping("/visitor/grims")
    public ResponseEntity<Integer> updateTitle(
            @CurrentMember Member member,
            @RequestBody UpdateTitle updateTitle) {
        updateGrimService.updateTitle(member, updateTitle);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
