package umc.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.domain.mission.converter.MissionConverter;
import umc.domain.mission.dto.MissionResDTO;
import umc.domain.mission.entity.mapping.MemberMission;
import umc.domain.mission.enums.MissionStatus;
import umc.domain.mission.exception.code.MissionSuccessCode;
import umc.domain.mission.service.MissionQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.BaseSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionQueryService missionQueryService;

    //미션 목록 조회
    @GetMapping("/userMissions")
    @Operation(summary = "미션 목록 조회")
    public ApiResponse<MissionResDTO.MissionPreviewListDTO> getMyMissions(
            @RequestParam(name = "memberId") Long memberId,
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "cursor", required = false) Integer cursor
    ){
        BaseSuccessCode code = MissionSuccessCode.MISSION_OK;

        //page로 받아옴
        Page<MemberMission> memberMissionPage = missionQueryService.getMyMissions(memberId, status, cursor);
        //converter이용해서 DTO형식으로 반환
        MissionResDTO.MissionPreviewListDTO result = MissionConverter.toMissionPreviewListDTO(memberMissionPage);
        //결과 반환
        return ApiResponse.onSuccess(code, result);
    }
/*
    @PatchMapping("/missions/{userMissionId}")
    @Operation(summary = "미션 완료 처리")
    public ApiResponse<MissionResDTO.ChangeStatusDTO> completeMission(
            @PathVariable(name = "userMissionId") Long userMissionId
    ){
        BaseSuccessCode code = MissionSuccessCode.MISSION_OK;
        MissionResDTO.ChangeStatusDTO result = missionService.completeMission(memberId, userMissionId);
        return ApiResponse.onSuccess(code, result);
    }

 */

}
