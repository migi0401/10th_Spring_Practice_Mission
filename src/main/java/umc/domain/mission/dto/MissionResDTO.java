package umc.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class MissionResDTO {

    @Builder
    public record MissionPreviewListDTO(
            List<MissionPreviewDTO> missionList,
            Long nextCursor,
            Boolean hasNext
    ){}

    @Builder
    public record MissionPreviewDTO(
            Long missionId,
            String storeName,
            String category,
            Integer dDay,
            String missionContent,
            Integer missionPoint,

            String status
    ){}

    @Builder
    public record ChangeStatusDTO(
        Long userMissionId,
        String status
    ){}

    @Builder
    public record Pagination<T>(
            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ){}

}
