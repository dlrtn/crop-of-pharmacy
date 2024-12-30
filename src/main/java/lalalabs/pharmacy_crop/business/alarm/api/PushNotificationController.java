package lalalabs.pharmacy_crop.business.alarm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.push_notification.application.PushNotificationService;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.dto.PushNotificationToken;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "푸시 알림", description = "푸시 알림 관련 기능들을 제공합니다.")
@RequestMapping("/push-notification")
@RestController
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @ApiHeader
    @Operation(summary = "푸시 알림 토큰 등록", description = "존재하지 않는 경우, 신규 등록하며 이미 존재하는 경우, 업데이트합니다.")
    @PostMapping("/tokens")
    public ResponseEntity<ApiResponse> registerPushNotificationToken(
            @AuthenticationPrincipal OauthUserDetails user,
            @RequestBody PushNotificationToken token
    ) {
        pushNotificationService.registerToken(user.getUser().getId(), token);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "푸시 알림 발송", description = "푸시 알림을 발송합니다.")
    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendPushNotification(
            @AuthenticationPrincipal OauthUserDetails user,
            @RequestBody PushNotificationBody body
    ) {
        pushNotificationService.send(user.getUser().getId(), body);

        return ResponseEntity.ok(SuccessResponse.of());
    }
}

