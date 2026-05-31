package umc.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.domain.inquiry.entity.Inquiry;
import umc.domain.member.entity.mapping.MemberFood;
import umc.domain.member.entity.mapping.MemberPolicy;
import umc.domain.member.enums.Gender;
import umc.domain.member.enums.SocialType;
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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "address")
    private String address;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "photo")
    private String photoUrl;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "userPoint")
    private Integer userPoint;

    @Column(name = "social_uid", nullable = false)
    private String socialUid;

    @Column(name = "social_provider")
    private String socialProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry>  inquiryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPolicy> memberPolicyList  = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberFood> memberFoodList = new ArrayList<>();

   }
