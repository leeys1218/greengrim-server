package com.greengrim.green.core.grim.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.common.entity.SortOption;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.core.grim.dto.GrimResponseDto.GrimInfo;
import com.greengrim.green.core.grim.service.GetGrimService;
import com.greengrim.green.core.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetGrimController {

    private final GetGrimService getGrimService;


    /**
     * [GET] 그림 목록 조회
     */
    @Operation(summary = "그림 목록 조회")
    @GetMapping("/grims")
    public ResponseEntity<PageResponseDto<List<GrimInfo>>> getGrims(
            @CurrentMember Member member,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") SortOption sortOption) {
        return ResponseEntity.ok(getGrimService.getGrims(member, page, size, sortOption));
    }

    /**
     * [GET] 내 그림 목록 조회
     */
    @Operation(summary = "내 그림 목록 조회")
    @GetMapping("/visitor/grims")
    public ResponseEntity<PageResponseDto<List<GrimInfo>>> getMyGrims(
            @CurrentMember Member member,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") SortOption sortOption) {
        return ResponseEntity.ok(getGrimService.getMyGrims(member, page, size, sortOption));
    }
}
