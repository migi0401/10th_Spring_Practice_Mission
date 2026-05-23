package umc.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.domain.member.converter.MemberConverter;
import umc.domain.member.dto.MemberReqDTO;
import umc.domain.member.dto.MemberResDTO;
import umc.domain.member.entity.Member;
import umc.domain.member.exception.code.MemberSuccessCode;
import umc.domain.member.service.MemberCommandService;
import umc.domain.member.service.MemberQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.BaseSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;


    @PostMapping("/sign-up")
    @Operation(summary = "회원가입")
    public ApiResponse<MemberResDTO.JoinResultDTO> join(
            @RequestBody @Valid MemberReqDTO.JoinDTO request
    ) {
        MemberResDTO.JoinResultDTO result = memberCommandService.join(request);
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }



    @GetMapping("/me")
    @Operation(summary = "마이페이지 프로필 조회")
    public ApiResponse<MemberResDTO.MyProfileDTO> getMyProfile(
            @RequestParam (name = "memberId") Long memberId
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        //회원 엔티티 조회
        Member member = memberQueryService.getMyProfile(memberId);

        //컨버터 이용
        MemberResDTO.MyProfileDTO result = MemberConverter.toMyProfileDTO(member);

        return ApiResponse.onSuccess(code, result);
    }

}
