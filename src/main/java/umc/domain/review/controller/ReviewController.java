package umc.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.domain.review.dto.ReviewReqDTO;
import umc.domain.review.dto.ReviewResDTO;
import umc.domain.review.exception.code.ReviewSuccessCode;
import umc.domain.review.service.ReviewCommandService;
import umc.domain.review.service.ReviewQueryService;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.BaseSuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/{storeId}/reviews")
    @Operation(summary = "가게 리뷰 작성")
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @PathVariable(name = "storeId") Long StoreId,
            @RequestBody @Valid ReviewReqDTO.ReviewDTO request
    ){
        BaseSuccessCode code = ReviewSuccessCode.OK;
        ReviewResDTO.CreateReviewResultDTO result = reviewCommandService.createReview(StoreId, request);
        return ApiResponse.onSuccess(code, result);
    }

    @GetMapping("/members/{memberId}/reviews")
    @Operation(summary = "가게 리뷰 조회")
    public ApiResponse<ReviewResDTO.Pagination<ReviewResDTO.ReviewsDTO>> getReview(
            @PathVariable Long memberId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ){
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewQueryService.getMyReviews(memberId, pageSize, cursor, query));
    }

}
