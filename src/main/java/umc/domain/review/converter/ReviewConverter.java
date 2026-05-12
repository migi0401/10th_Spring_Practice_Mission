package umc.domain.review.converter;

import umc.domain.Store.entity.Store;
import umc.domain.member.entity.Member;
import umc.domain.review.dto.ReviewReqDTO;
import umc.domain.review.dto.ReviewResDTO;
import umc.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {
    public static Review toReview(ReviewReqDTO.ReviewDTO request, Member member, Store store){
        return Review.builder()
                .score(request.score())
                .content(request.text())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.CreateReviewResultDTO toReviewResDTO(Review review){
        return ReviewResDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewsDTO toReviewsDTO(Review review){
        return ReviewResDTO.ReviewsDTO.builder()
                .memberId(review.getMember().getId())
                .reviewId(review.getId())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }

    public static<T> ReviewResDTO.Pagination<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return ReviewResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }
}
