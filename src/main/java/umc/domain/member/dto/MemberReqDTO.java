package umc.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import umc.domain.member.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record JoinDTO(
            String name,
            Gender gender,
            String address,
            LocalDate birth,
            String nickname,
            String mail,
            String password,
            String phoneNumber,
            List<Long> foodList,
            @NotNull(message = "약관 동의 정보는 필수입니다.")
            AgreeDTO agree
    ){}

    public record AgreeDTO(
      @NotNull boolean age,
      @NotNull boolean service,
      @NotNull boolean privacy,
      boolean location,
      boolean marketing
    ){}
}
