package lalalabs.pharmacy_crop.business.authorization.api.docs;

public final class ApiDescriptions {

    private ApiDescriptions() {}

    public static final String GET_USER_SUMMARY = "사용자 조회";
    public static final String GET_USER_DESCRIPTION = "사용자의 정보를 조회합니다.";

    public static final String GET_USER_NICKNAME_SUMMARY = "사용자 닉네임 조회";
    public static final String GET_USER_NICKNAME_DESCRIPTION = "사용자의 닉네임을 조회합니다.";

    public static final String SOCIAL_LOGIN_SUMMARY = "소셜 로그인";
    public static final String SOCIAL_LOGIN_DESCRIPTION = "소셜 서버가 해당 엔드포인트로 인증 코드와 함께 요청을 보낼 때, 이를 받아 웹 애플리케이션 서버 자체의 JWT 토큰을 발급하여 반환합니다. 클라이언트 측에서 호출할 일은 없습니다.";

    public static final String WITHDRAW_SOCIAL_USER_SUMMARY = "소셜 사용자 탈퇴";
    public static final String WITHDRAW_SOCIAL_USER_DESCRIPTION = "소셜 사용자의 회원 탈퇴를 수행 후, 소셜 서버와 연결을 끊습니다.";

    public static final String REFRESH_TOKEN_SUMMARY = "사용자 토큰 갱신";
    public static final String REFRESH_TOKEN_DESCRIPTION = "사용자의 Access 토큰을 갱신합니다. 이 때, 유효한 Refresh 토큰이 필요합니다.";
}
