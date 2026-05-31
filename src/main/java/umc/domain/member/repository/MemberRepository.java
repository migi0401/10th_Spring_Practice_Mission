package umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.member.entity.Member;
import umc.domain.member.enums.SocialType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMail(String mail);

    boolean existsByMail(String mail);

    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String username);
}
