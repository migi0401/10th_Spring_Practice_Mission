package umc.domain.member.entity.mapping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Component;
import umc.domain.member.entity.Member;
import umc.domain.policy.entity.Policy;
import umc.global.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_at", nullable = false)
    private LocalDateTime policyAt;

    @Column(name = "is_agreed", nullable = false)
    private Boolean isAgreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getMemberPolicyList().remove(this);
        }
        this.member = member;
        member.getMemberPolicyList().add(this);
    }
}
