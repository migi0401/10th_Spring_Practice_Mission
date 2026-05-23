package umc.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record JoinResultDTO(
            Long userId,
            LocalDateTime createdAt
    ){}
    //마이페이지 조회용
    public record MyProfileDTO(
            String nickname,
            String profileImageUrl,
            String email,
            String phoneNumber,
            Boolean isPhoneVerified,
            Integer point
    ){}
}
