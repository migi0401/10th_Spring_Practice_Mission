package umc.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record CreateReviewResultDTO(
            Long reviewId,
            LocalDateTime createdAt
    ){}

    @Builder
    public record ReviewResultDTO(
            Long reviewId,
            LocalDateTime createdAt
    ){}

    @Builder
    public record ReviewsDTO(
            Long memberId,
            Long reviewId,
            Float score,
            LocalDateTime createdAt,
            String content
    ){}

    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
