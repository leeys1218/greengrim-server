package com.greengrim.green.core.grim.service;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.errorCode.GrimErrorCode;
import com.greengrim.green.common.fcm.FcmService;
import com.greengrim.green.core.grim.Grim;
import com.greengrim.green.core.grim.dto.GrimRequestDto.RegisterGrimInfo;
import com.greengrim.green.core.grim.dto.GrimResponseDto.GrimInfo;
import com.greengrim.green.core.grim.repository.GrimRepository;
import com.greengrim.green.core.member.Member;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RegisterGrimService {

  private final WebClient webClient;
  private final GrimRepository grimRepository;
  private final FcmService fcmService;

  public void save(Grim grim) { grimRepository.save(grim); }

  public GrimInfo register(Member member, String imgUrl) {
    Grim grim = Grim.builder()
        .title("untitled")
        .imgUrl(imgUrl)
        .status(true)
        .member(member)
        .build();
    save(grim);
    return new GrimInfo(grim);
  }

  public void generateGrim(Member member, RegisterGrimInfo registerGrimInfo) {

    if(!checkGreenPoint(member)) {
      throw new BaseException(GrimErrorCode.NOT_ENOUGH_POINT);
    }

    String prompt = registerGrimInfo.getStyle() + registerGrimInfo.getNoun() + registerGrimInfo.getVerb();
    Mono<String> resultMono = webClient.post()
        .uri("/txt2img")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(Map.of(
            "prompt", prompt,
            "guidance_scale", 7.5,
            "height", 512,
            "width", 512,
            "num_inference_steps", 50,
            "safety_check", true,
            "seed", 1
        ))
        .retrieve()
        .bodyToMono(String.class);

    resultMono.subscribe(
        result -> {
          System.out.println("Success: " + result);
          member.minusPoint(500);
          fcmService.sendGrimGenerationSuccess(register(member, result), member.getFcmToken());
        },
        error -> {
          System.err.println("Error: " + error.getMessage());
          fcmService.sendGrimGenerationFail(member.getFcmToken());
        }
    );
  }

  public boolean checkGreenPoint(Member member) {
    if(member.getPoint() < 500) {
      return false;
    }
    return true;
  }

}
