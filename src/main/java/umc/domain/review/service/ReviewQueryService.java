package umc.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.review.converter.ReviewConverter;
import umc.domain.review.dto.ReviewResDTO;
import umc.domain.review.entity.Review;
import umc.domain.review.exception.ReviewException;
import umc.domain.review.exception.code.ReviewErrorCode;
import umc.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly=true)
    public ReviewResDTO.Pagination<ReviewResDTO.ReviewsDTO> getMyReviews(
            Long memberId,
            Integer pageSize,
            String cursor,
            String query
    ){
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        long idCursor;
        Slice<Review> reviewList = null;
        String nextCursor = "-1";

        if(!cursor.equals("-1")){

            String[] cursorSplit = cursor.split(":");
            switch(query.toLowerCase()){
                case "id":

                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    //
                    reviewRepository.findReviewsByMemberIdAndIdLessThanOrderByIdDesc(
                            memberId,
                            idCursor,
                            pageRequest
                    );
                    break;

                case "score":
                    Float scoreCursor = Float.parseFloat(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    reviewList = reviewRepository.findReviewsByScoreCursor(memberId, scoreCursor, idCursor, pageRequest);
                    break;

                default:
                    throw new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND);
            }

        }else{//커서 없을 때 첫 페이지 조회
            if(query.equalsIgnoreCase("score")){
                reviewList = reviewRepository.findFirstPageByScore(memberId, pageRequest);
            }else{
                reviewList = reviewRepository.findReviewsByMemberIdOrderByIdDesc(memberId, pageRequest);
            }
        }

        //다음 커서 로직
        if(reviewList != null && !reviewList.isEmpty() && reviewList.hasNext()){
            Review lastReview = reviewList.getContent().get(reviewList.getContent().size()-1);

            if(query.equalsIgnoreCase("score")){
                nextCursor = lastReview.getScore() + ":" + lastReview.getId();
            }else{
                nextCursor = lastReview.getId() + ":" + lastReview.getId();
            }
        }

        return ReviewConverter.toPagination(
                reviewList.map(ReviewConverter::toReviewsDTO).toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize()
        );
    }
}
