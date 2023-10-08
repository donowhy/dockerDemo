package chatting.demo.domain.member.service;

import chatting.demo.domain.member.entity.Member;
import chatting.demo.domain.member.entity.enums.Role;
import chatting.demo.domain.member.repository.MemberRepository;
import chatting.demo.domain.member.service.dto.LoginRequest;
import chatting.demo.domain.member.service.dto.LoginResponse;
import chatting.demo.domain.member.service.dto.MemberResponse;
import chatting.demo.domain.member.service.dto.MemberSaveRequestDto;
import chatting.demo.global.common.error.code.ErrorCode;
import chatting.demo.global.common.error.exception.BusinessException;
import chatting.demo.global.common.jwt.JwtProvider;
import chatting.demo.global.common.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final S3Uploader s3Uploader;


    @Value("${cloud.aws.cloud.url}")
    private String basicProfile;

    // 유저 저장
    public String saveSeller(MemberSaveRequestDto memberSaveRequestDto) {

        String profileImage = basicProfile + "/%E1%84%86%E1%85%B5%E1%84%8B%E1%85%A5%E1%84%8F%E1%85%A2%E1%86%BA.jpeg";

        Member member = Member.builder()
                .email(memberSaveRequestDto.getEmail())
                .password(memberSaveRequestDto.getPassword())
                .nickname(memberSaveRequestDto.getNickname())
                .profileUrl(profileImage)
                .role(Role.SELLER)
                .build();


        memberRepository.save(member);

        return memberSaveRequestDto.getEmail();
    }

    public String saveUser(MemberSaveRequestDto memberSaveRequestDto) {

        String profileImage = basicProfile + "/%E1%84%86%E1%85%B5%E1%84%8B%E1%85%A5%E1%84%8F%E1%85%A2%E1%86%BA.jpeg";

        Member member = Member.builder()
                .email(memberSaveRequestDto.getEmail())
                .password(memberSaveRequestDto.getPassword())
                .nickname(memberSaveRequestDto.getNickname())
                .profileUrl(profileImage)
                .role(Role.USER)
                .build();


        memberRepository.save(member);

        return memberSaveRequestDto.getEmail();
    }

    // 유저 정보 조회
    public MemberResponse getUserInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    // 로그인
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_USER_DATA)
        );

        if (!member.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException();
        }

        // 로그인 시 엑세스 토큰, 리프레시 토큰 발급
        String accessToken = jwtProvider.createAccessToken(member);
        String refreshToken = jwtProvider.createRefreshToken(member);

//        RefreshToken newRefreshToken = RefreshToken.builder()
//                .refreshToken(refreshToken)
//                .memberId(member.getId())
//                .build();

        // 레디스에 리프레시 토큰 저장
//        refreshTokenRepository.save(newRefreshToken);

        member.setRefreshToken(refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void delete(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_VALID_DATA));

        memberRepository.delete(member);
    }

//    // 닉네임 변경
//    public UpdateUserResponseDto updateNickname(UpdateUserRequestDto updateUserRequestDto, Long id) {
//        Member member = memberRepository.findById(id).orElseThrow(() ->
//                new BusinessException(ErrorCode.NOT_EXISTS_USER_ID)
//        );
//
//        member.setNickname(updateUserRequestDto.getNickName());
//
//        return UpdateUserResponseDto.builder()
//                .email(member.getEmail())
//                .nickname(member.getNickname())
//                .build();
//    }
//
//
//    // 유저 accessToken 재발급
//    public String getAccessToken(AccessTokenRequest request) {
//        AccessTokenResponse accessTokenResponse = tokenService.generateAccessToken(request);
//
//        return accessTokenResponse.getAccessToken();
//    }
//
//    // 비밀번호 변경
//    public void changeMyPassword(Long id, ChangeMyPasswordRequestDto requestDto) {
//        Member member = memberRepository.findById(id).orElseThrow(() ->
//                new BusinessException(ErrorCode.NOT_EXISTS_USER_ID)
//        );
//        if (passwordEncoder.encrypt(member.getEmail(), requestDto.getNowPassword()).equals(member.getPassword()) &&
//                requestDto.getPasswordOne().equals(requestDto.getPasswordTwo())) {
//            member.setUserPassword(passwordEncoder.encrypt(member.getEmail(), member.getPassword()));
//        }
//    }
//
//
//    public UpdateProfilePictureDto updateProfilePicture(MultipartFile multipartFile, Long id) throws IOException {
//
//        Member member = memberRepository.findById(id).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID)
//        );
//
//        String upload = s3Uploader.upload(multipartFile, "Profile");
//
//        member.setProfileImageUrl(upload);
//
//        return new UpdateProfilePictureDto(upload);
//    }
}
