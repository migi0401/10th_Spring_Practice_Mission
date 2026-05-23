package umc.domain.member.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.global.apiPayload.code.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "해당 사용자를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.ALREADY_REPORTED,
            "MEMBER404_2",
            "이미 존재하는 사용자입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
