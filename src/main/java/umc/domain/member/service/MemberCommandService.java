package umc.domain.member.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.food.entity.Food;
import umc.domain.food.repository.FoodRepository;
import umc.domain.member.converter.MemberConverter;
import umc.domain.member.dto.MemberReqDTO;
import umc.domain.member.dto.MemberResDTO;
import umc.domain.member.entity.Member;
import umc.domain.member.entity.mapping.MemberFood;
import umc.domain.member.exception.MemberException;
import umc.domain.member.exception.code.MemberErrorCode;
import umc.domain.member.repository.MemberRepository;
import umc.domain.policy.entity.Policy;
import umc.domain.policy.repository.PolicyRepository;
import umc.global.security.entity.AuthMember;
import umc.global.security.util.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final PolicyRepository policyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberResDTO.JoinResultDTO join(MemberReqDTO.JoinDTO request){
        //비밀번호 암호화
        if(memberRepository.existsByMail(request.mail())){
            throw new MemberException(MemberErrorCode.MEMBER_ALREADY_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member newMember = MemberConverter.toMember(request, encodedPassword);
        //DB에 있는 음식 연결
        List<Food> foodList = foodRepository.findAllByIdIn(request.foodList());
        MemberConverter.toMemberFoodList(foodList, newMember); //사용자 매핑 테이블에 추가
        //DB에 있는 약관 연결
        List<String> agreedPolicyNames = MemberConverter.toAgreedPolicyNames(request.agree());
        List<Policy> policyList = policyRepository.findAllByNameIn(agreedPolicyNames); //사용자 약관 테이블에 추가
        //동의한 약관만 볼 수 있게한다.
        MemberConverter.toMemberPolicyList(policyList, newMember);
        //DB저장
        Member savedMember = memberRepository.save(newMember);
        //결과 DTO 전송
        return MemberConverter.toJoinResultDTO(savedMember);
    }

    public MemberResDTO.LoginResultDTO login(MemberReqDTO.LoginReqDTO request) {
        Member member = memberRepository.findByMail(request.mail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(request.password(), member.getPassword())){
            throw new MemberException(MemberErrorCode.MEMBER_INVALID_PASSWORD);
        }

        //accseeToken 있어야 함.
        AuthMember authMember = new AuthMember(member);
        String accessToken = jwtUtil.createAccessToken(authMember);

        return new MemberResDTO.LoginResultDTO(accessToken);
    }
}
