package lalalabs.pharmacy_crop.business.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.authorization.api.docs.ApiDescriptions;
import lalalabs.pharmacy_crop.business.authorization.application.OauthService;
import lalalabs.pharmacy_crop.business.user.api.dto.UpdateUserRequest;
import lalalabs.pharmacy_crop.business.user.api.dto.UserDto;
import lalalabs.pharmacy_crop.business.user.application.UserCommandService;
import lalalabs.pharmacy_crop.business.user.application.UserQueryService;
import lalalabs.pharmacy_crop.business.user.application.UserService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "사용자", description = "사용자의 정보 변경, 관리를 수행합니다.")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final OauthService oauthService;
    private final UserQueryService userQueryService;
    private final UserService userService;

    @ApiHeader
    @Operation(summary = "닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    @PatchMapping("/nickname")
    public ResponseEntity<ApiResponse> changeNickname(
            @AuthenticationPrincipal OauthUserDetails oauthUser,
            @RequestBody UpdateUserRequest nickname
    ) {
        userCommandService.updateUserNickname(oauthUser.getUser(), nickname);

        return ResponseEntity.ok().build();
    }

    @ApiHeader
    @Operation(
            summary = ApiDescriptions.WITHDRAW_SOCIAL_USER_SUMMARY,
            description = ApiDescriptions.WITHDRAW_SOCIAL_USER_DESCRIPTION
    )
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawUser(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        userCommandService.withdrawUser(oauthUser.getUser());
        oauthService.unlink(oauthUser.getUser());

        return ResponseEntity.ok().body(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(
            summary = ApiDescriptions.GET_USER_NICKNAME_SUMMARY,
            description = ApiDescriptions.GET_USER_NICKNAME_DESCRIPTION
    )
    @GetMapping("/nickname")
    public ResponseEntity<ApiResponse> getUserNickname(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        String nickname = userQueryService.getUserNickname(oauthUser.getUser());

        return ResponseEntity.ok(SuccessResponse.of(nickname));
    }

    @ApiHeader
    @Operation(
            summary = ApiDescriptions.GET_USER_SUMMARY,
            description = ApiDescriptions.GET_USER_DESCRIPTION
    )
    @GetMapping()
    public ResponseEntity<ApiResponse> getUserInfo(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        UserDto userDto = userService.getUser(oauthUser.getUser().getId());

        return ResponseEntity.ok(SuccessResponse.of(userDto));
    }

    @ApiHeader
    @Operation(
            summary = ApiDescriptions.GET_USER_LIST_SUMMARY,
            description = ApiDescriptions.GET_USER_LIST_DESCRIPTION
    )
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getUserList(@AuthenticationPrincipal OauthUserDetails oauthUser, @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page, @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size) {
        return ResponseEntity.ok(SuccessResponse.of(userService.getUserList(page, size)));
    }
}
