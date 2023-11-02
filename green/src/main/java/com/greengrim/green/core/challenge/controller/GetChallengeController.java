package com.greengrim.green.core.challenge.controller;

import com.greengrim.green.common.auth.CurrentMember;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.core.challenge.Category;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeDetailInfo;
import com.greengrim.green.core.challenge.dto.ChallengeResponseDto.ChallengeSimpleInfo;
import com.greengrim.green.core.challenge.service.GetChallengeService;
import com.greengrim.green.core.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetChallengeController {

    private final GetChallengeService getChallengeService;

    /**
     * [GET] 챌린지 상세 조회
     */
    @GetMapping("/challenges/{id}")
    public ResponseEntity<ChallengeDetailInfo> getChallengeInfo(
            @CurrentMember Member member,
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                getChallengeService.getChallengeDetail(member, id),
                HttpStatus.OK);
    }

    /**
     * [GET] 카테고리 별 챌린지 목록 조회
     */
    @GetMapping("/challenges")
    public ResponseEntity<PageResponseDto<List<ChallengeSimpleInfo>>> getChallengesByCategory(
            @CurrentMember Member member,
            @RequestParam(value = "category") Category category,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort) {
        return ResponseEntity.ok(getChallengeService.getChallengesByCategory(
                member, category, page, size, sort));
    }

    /**
     * [GET] 내가 만든 챌린지 목록 조회
     */
    @GetMapping("/visitor/challenges")
    public ResponseEntity<PageResponseDto<List<ChallengeSimpleInfo>>> getMyChallenges(
            @CurrentMember Member member,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort) {
        return ResponseEntity.ok(getChallengeService.getMyChallenges(
                member, page, size, sort));
    }

}
