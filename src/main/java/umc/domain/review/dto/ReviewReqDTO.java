package umc.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class ReviewReqDTO {

    @Builder
    public record ReviewDTO(
            @NotNull(message = "로그인은 필수입니다.")
            Long memberId,
            @NotNull(message = "리뷰 평점은 필수입니다.")
            Float score,
            @NotNull(message = "리뷰는 빈칸일 수 없습니다.")
            String text
    ){}
}
