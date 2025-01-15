package lalalabs.pharmacy_crop.business.alarm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.alarm.api.dto.NotificationDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "알림", description = "알림 관련 기능들을 제공합니다.")
@RequestMapping()
@RestController
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @ApiHeader
    @Operation(summary = "푸시 알림 토큰 등록", description = "존재하지 않는 경우, 신규 등록하며 이미 존재하는 경우, 업데이트합니다.")
    @PostMapping("/push-notification/tokens")
    public ResponseEntity<ApiResponse> registerPushNotificationToken(@AuthenticationPrincipal OauthUserDetails user, @RequestBody PushNotificationToken token) {
        pushNotificationService.registerToken(user.getUser().getId(), token);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "푸시 알림 발송", description = "푸시 알림을 발송합니다.")
    @PostMapping("/push-notification/send")
    public ResponseEntity<ApiResponse> sendPushNotification(@AuthenticationPrincipal OauthUserDetails user, @RequestBody PushNotificationBody body) {
        pushNotificationService.send(user.getUser().getId(), body);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "푸시 알림 수신 여부 수정", description = "푸시 알림 수신 여부를 수정합니다.")
    @PatchMapping("/push-notification/tokens")
    public ResponseEntity<ApiResponse> updatePushNotificationAgree(
            @AuthenticationPrincipal OauthUserDetails user,
            @RequestBody PushNotificationToken token
    ) {
        pushNotificationService.updateAgreePushNotification(user.getUser().getId(), token);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "수신한 알림 조회", description = "수신한 알림을 조회합니다.")
    @GetMapping("/notification/")
    public ResponseEntity<ApiResponse> getPushNotificationAgree(@AuthenticationPrincipal OauthUserDetails user) {
        List<NotificationDto> notificationDtoList = pushNotificationService.getNotifications(user.getUserId());

        return ResponseEntity.ok(SuccessResponse.of(notificationDtoList));
    }

    @ApiHeader
    @Operation(summary = "수신한 알림 상태 변경", description = "수신한 알림의 상태를 변경합니다.")
    @PatchMapping("/notification/{notificationId}")
    public ResponseEntity<ApiResponse> updatePushNotificationAgree(@AuthenticationPrincipal OauthUserDetails user, @PathVariable int notificationId) {
        pushNotificationService.readNotification(user.getUserId(), notificationId);

        return ResponseEntity.ok(SuccessResponse.of());
    }
}
