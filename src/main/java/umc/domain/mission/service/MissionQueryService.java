package umc.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.member.entity.Member;
import umc.domain.member.exception.MemberException;
import umc.domain.member.exception.code.MemberErrorCode;
import umc.domain.member.repository.MemberRepository;
import umc.domain.mission.converter.MissionConverter;
import umc.domain.mission.dto.HomeResDTO;
import umc.domain.mission.dto.MissionResDTO;
import umc.domain.mission.entity.Mission;
import umc.domain.mission.entity.mapping.MemberMission;
import umc.domain.mission.enums.MissionStatus;
import umc.domain.mission.exception.MissionException;
import umc.domain.mission.exception.code.MissionErrorCode;
import umc.domain.mission.repository.MemberMissionRepository;
import umc.domain.mission.repository.MissionRespository;
import umc.domain.region.entity.City;
import umc.domain.region.repository.CityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final CityRepository cityRepository;
    private final MissionRespository missionRespository;

    public MissionResDTO.Pagination<MissionResDTO.MissionPreviewDTO> getMyMissions(
            Long memberId,
            MissionStatus status,
            Integer pageSize,
            Integer pageNumber,
            String sort
            )
    {

         Sort sortInfo;
         if(sort != null){
             sortInfo = Sort.by(sort);
         }else{
             sortInfo = Sort.by("id").descending();
         }

        //멤버 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
         //페이지 정보들을 PageRequest로
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortInfo);
        //내 미션들 조회
        Page<MemberMission> myMissionList = memberMissionRepository.findAllByMemberAndStatus(member, status, pageRequest);

        //미션들 응답 DTO로 포장
        return MissionConverter.toPagination(
                myMissionList.map(MissionConverter::toMissionPreviewDTO).toList(),
                myMissionList.getNumber(),
                myMissionList.getSize()
        );
    }

    //홈화면 하단 미션 정보
    public Page<Mission> getHomeInfo(Long memberId, String cityName, Integer page){
        City city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new  MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);

        return missionRespository.findAvailableMissionsByCity(cityName, memberId, pageRequest);
    }

    //홈 화면 상단 정보
    public HomeResDTO.HomeSummaryDTO getHomeSummary(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new  MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Integer completedMissions = memberMissionRepository.countByMemberAndStatus(member, MissionStatus.SUCCESS);

        Integer targetCount = 10;
        Integer currentCount = completedMissions % targetCount;

        return new HomeResDTO.HomeSummaryDTO(
                member.getUserPoint(),
                currentCount,
                targetCount,
                1000
        );
    }
}
