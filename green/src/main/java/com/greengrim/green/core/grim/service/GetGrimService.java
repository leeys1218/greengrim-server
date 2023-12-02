package com.greengrim.green.core.grim.service;

import static com.greengrim.green.common.entity.SortOption.ASC;
import static com.greengrim.green.common.entity.SortOption.DESC;

import com.greengrim.green.common.entity.SortOption;
import com.greengrim.green.common.entity.dto.PageResponseDto;
import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.GlobalErrorCode;
import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.grim.dto.GrimResponseDto.GrimInfo;
import com.greengrim.green.core.grim.repository.GrimRepository;
import com.greengrim.green.core.member.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGrimService {

    private final GrimRepository grimRepository;

    public PageResponseDto<List<GrimInfo>> getGrims(Member member, int page, int size, SortOption sortOption) {
        List<GrimInfo> grimInfos = new ArrayList<>();

        Page<Grim> grims = grimRepository.findAllStatusIsTrue(getPageable(page, size, sortOption));

        grims.forEach(grim -> grimInfos.add(new GrimInfo(grim)));

        return new PageResponseDto<>(grims.getNumber(), grims.hasNext(), grimInfos);
    }

    private Pageable getPageable(int page, int size, SortOption sort) {
        if (sort == DESC) { // 최신순
            return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        } else if (sort == ASC) { // 오래된순
            return PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        } else {
            throw new BaseException(GlobalErrorCode.NOT_VALID_ARGUMENT_ERROR);
        }
    }
}
