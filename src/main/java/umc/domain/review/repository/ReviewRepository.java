package umc.domain.review.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.domain.review.entity.Review;
import umc.domain.Store.entity.Store;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStore(Store store);

    Slice<Review> findReviewsByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, long idCursor, PageRequest pageRequest);

    Slice<Review> findReviewsByMemberIdOrderByIdDesc(Long memberId, PageRequest pageRequest);

    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId " +
            "AND (r.score < :scoreCursor OR (r.score = :scoreCursor AND r.id < :idCursor)) " +
            "ORDER BY r.score DESC, r.id DESC")
    Slice<Review> findReviewsByScoreCursor(Long memberId, Float scoreCursor, long idCursor, PageRequest pageRequest);

    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId ORDER BY r.score DESC, r.id DESC")
    Slice<Review> findFirstPageByScore(Long memberId, PageRequest pageRequest);
}
