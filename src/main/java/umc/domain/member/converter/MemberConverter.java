package umc.domain.member.converter;

import umc.domain.food.entity.Food;
import umc.domain.member.dto.MemberReqDTO;
import umc.domain.member.dto.MemberResDTO;
import umc.domain.member.entity.Member;
import umc.domain.member.entity.mapping.MemberFood;
import umc.domain.member.entity.mapping.MemberPolicy;
import umc.domain.member.enums.Gender;
import umc.domain.member.enums.SocialType;
import umc.domain.policy.entity.Policy;
import umc.global.security.dto.OAuthDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberResDTO.MyProfileDTO toMyProfileDTO(Member member){
        return new MemberResDTO.MyProfileDTO(
                member.getNickname(),
                member.getPhotoUrl(),
                member.getMail(),
                member.getPhoneNumber(),
                member.getPhoneNumber() != null && !member.getPhoneNumber().isEmpty(),
                member.getUserPoint()
        );
    }

    //일반 회원 전용
    public static Member toMember(MemberReqDTO.JoinDTO request, String encodedPassword){
        return Member.builder()
                .name(request.name())
                .gender(request.gender())
                .address(request.address())
                .birth(request.birth())
                .nickname(request.nickname())
                .photoUrl("")//임시 저장
                .mail(request.mail())
                .userPoint(0) //임시로 유저 포인트 0으로 저장
                .socialUid("")//임시 저장
                .socialProvider("")//임시 저장
                .password(encodedPassword)
                .phoneNumber(request.phoneNumber())
                .build();
    }

    //소셜 로그인 전용
    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .name(dto.getName())// 카카오는 닉네임을 주므로 이름/닉네임 동일하게 세팅
                .mail(dto.getSocialEmail())
                .socialUid(dto.getSocialUid())
                .socialType(dto.getSocialType()) // 서비스에서 넘겨준 KAKAO, NAVER 등
                // 👇 아래는 DB 필수값(nullable = false)을 통과하기 위한 더미(임시) 데이터들입니다.
                .gender(Gender.NONE)
                .build();
    }

    public static MemberFood toMemberFood(Food food){
        return MemberFood.builder()
                .food(food)
                .build();
    }

    public static MemberPolicy toMemberPolicy(Policy policy){
        return MemberPolicy.builder()
                .policy(policy)
                .build();
    }

    public static List<String>  toAgreedPolicyNames(MemberReqDTO.AgreeDTO agree){
        List<String> agreedPolicyNames = new ArrayList<>();
        if(agree.age()) agreedPolicyNames.add("AGE");
        if(agree.service()) agreedPolicyNames.add("SERVICE");
        if(agree.privacy()) agreedPolicyNames.add("PRIVACY");
        if(agree.location()) agreedPolicyNames.add("LOCATION");
        if(agree.marketing()) agreedPolicyNames.add("MARKETING");
        return agreedPolicyNames;
    }

    public static List<MemberFood> toMemberFoodList(List<Food> foodList, Member member){
        return foodList.stream()
                .map(food -> {
                    MemberFood memberFood = toMemberFood(food);
                    memberFood.setMember(member);
                    return memberFood;
                }).collect(Collectors.toList());
    }

    public static List<MemberPolicy> toMemberPolicyList(List<Policy> policyList, Member member){
        return policyList.stream()
                .map(policy -> {
                    MemberPolicy memberPolicy = toMemberPolicy(policy);
                    memberPolicy.setMember(member);
                    return memberPolicy;
                }).collect(Collectors.toList());
    }

    public static MemberResDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResDTO.JoinResultDTO.builder()
                .userId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.LoginResultDTO toLogin(String accessToken) {
        return MemberResDTO.LoginResultDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
