package chatting.demo.domain.member.controller;

import chatting.demo.domain.member.service.MemberService;
import chatting.demo.domain.member.service.dto.LoginRequest;
import chatting.demo.domain.member.service.dto.LoginResponse;
import chatting.demo.domain.member.service.dto.MemberResponse;
import chatting.demo.domain.member.service.dto.MemberSaveRequestDto;
import chatting.demo.global.common.jwt.MemberInfo;
import chatting.demo.global.common.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @Operation(summary = "회원가입", description = "회원가입 시 이메일, 패스워드, 내가 가지고 있는 cardlist를 request로 받는다", tags = {"Member Controller"})
    @PostMapping("/saveSeller")
    public String saveSeller(@RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto) {
        return memberService.saveSeller(memberSaveRequestDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @Operation(summary = "회원가입", description = "회원가입 시 이메일, 패스워드, 내가 가지고 있는 cardlist를 request로 받는다", tags = {"Member Controller"})
    @PostMapping("/save")
    public String save(@RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto) {
        return memberService.saveUser(memberSaveRequestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "226", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @Operation(summary = "로그인", description = "로그인 시 토큰 발급", tags = {"Member Controller"})
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "멤버 정보", description = "멤버 정보 불러오기", tags = {"Member Controller"})
    @GetMapping("/info")
    public MemberResponse getUser(@MemberInfo MembersInfo membersInfo) {
        return memberService.getUserInfo(membersInfo.getId());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "멤버 삭제", description = "멤버 정보 삭제", tags = { "Member Controller" })
    @DeleteMapping("/delete")
    public void delete (@MemberInfo MembersInfo membersInfo){
        memberService.delete(membersInfo.getId());
    }

}