package umc.domain.mission.converter;

import org.springframework.data.domain.Page;
import umc.domain.member.entity.Member;
import umc.domain.mission.dto.MissionResDTO;
import umc.domain.mission.entity.Mission;
import umc.domain.mission.entity.mapping.MemberMission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResDTO.MissionPreviewDTO toMissionPreviewDTO(MemberMission memberMission){

        return new MissionResDTO.MissionPreviewDTO(
                memberMission.getMission().getId(),
                memberMission.getMission().getStore().getName(),
                "카테고리명",
                7,
                memberMission.getMission().getContent(),
                memberMission.getMission().getMissionPoint(),
                memberMission.getStatus().name()
        );
    }

    public static MissionResDTO.MissionPreviewListDTO toMissionPreviewListDTO(Page<MemberMission> missionPage){

        List<MissionResDTO.MissionPreviewDTO> missionPreviewDTOList = missionPage.stream()
                .map(MissionConverter::toMissionPreviewDTO)
                .collect(Collectors.toList());

        Long nextCursor = null;
        if(missionPage.hasNext() && !missionPreviewDTOList.isEmpty()){
            nextCursor = (long) (missionPage.getNumber() + 1);
        }

        return new MissionResDTO.MissionPreviewListDTO(
                missionPreviewDTOList,
                nextCursor,
                missionPage.hasNext()
        );
    }

    public static <T> MissionResDTO.Pagination<T> toPagination(
            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ){
        return MissionResDTO.Pagination.<T>builder()
                .data(data)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }
}
