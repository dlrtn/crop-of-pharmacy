package lalalabs.pharmacy_crop.business.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.user.api.dto.UpdateUserRequest;
import lalalabs.pharmacy_crop.business.user.application.UserCommandService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자의 정보 변경, 관리를 수행합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;

    @ApiHeader
    @Operation(summary = "닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    @PatchMapping("/nickname")
    public ResponseEntity<ApiResponse> changeNickname(@AuthenticationPrincipal OauthUserDetails oauthUser,
                                                      @RequestBody UpdateUserRequest nickname) {
        userCommandService.updateUserNickname(oauthUser.getUser(), nickname);

        return ResponseEntity.ok().build();
    }
}
