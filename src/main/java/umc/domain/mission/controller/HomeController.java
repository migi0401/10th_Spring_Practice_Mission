package umc.domain.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.domain.mission.converter.HomeConverter;
import umc.domain.mission.dto.HomeResDTO;
import umc.domain.mission.entity.Mission;
import umc.domain.mission.exception.code.MissionSuccessCode;
import umc.domain.mission.service.MissionQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.BaseSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController { //API 2개로 나눠서 구현

    private final MissionQueryService missionQueryService;

    @GetMapping("/summary")
    @Operation(summary = "홈 화면 상단 정보")
    public ApiResponse<HomeResDTO.HomeSummaryDTO> getHomeSummary(
            @RequestHeader(name = "memberId") Long memberId
    ){
        BaseSuccessCode code = MissionSuccessCode.HOME_OK;
        HomeResDTO.HomeSummaryDTO result = missionQueryService.getHomeSummary(memberId);
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/missions")
    @Operation(summary = "홈 화면 미션 정보")
    public ApiResponse<HomeResDTO.RegionMissionListDTO> getHomeInfo(
            @RequestParam(name = "memberId") Long memberId,
            @RequestParam(name = "regionId") String regionName,
            @RequestParam(name = "cursor", required = false) Integer cursor
    ){
        BaseSuccessCode code = MissionSuccessCode.HOME_OK;
        //엔티티 page 받아오기
        Page<Mission> missionPage = missionQueryService.getHomeInfo(memberId, regionName, cursor);
        //컨버터 이용
        HomeResDTO.RegionMissionListDTO result = HomeConverter.toRegionMissionListDTO(missionPage);

        return ApiResponse.onSuccess(code, result);
    }
}
