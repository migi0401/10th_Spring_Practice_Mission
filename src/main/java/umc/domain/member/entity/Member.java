package umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.domain.inquiry.entity.Inquiry;
import umc.domain.member.enums.Gender;
import umc.domain.review.entity.Review;
import umc.global.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "photo", nullable = false)
    private String photoUrl;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "userPoint", nullable = false)
    private Integer userPoint;

    @Column(name = "social_uid", nullable = false)
    private String socialUid;

    @Column(name = "social_provider", nullable = false)
    private String socialProvider;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();
}
