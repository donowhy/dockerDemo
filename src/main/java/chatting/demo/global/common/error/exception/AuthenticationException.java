package chatting.demo.global.common.error.exception;


import chatting.demo.global.common.error.code.ErrorCode;
import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{
    private ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
