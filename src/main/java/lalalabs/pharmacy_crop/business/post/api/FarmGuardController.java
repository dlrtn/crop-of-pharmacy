package lalalabs.pharmacy_crop.business.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lalalabs.pharmacy_crop.business.post.api.dto.FarmGuardDetailDto;
import lalalabs.pharmacy_crop.business.post.api.dto.FarmGuardDto;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardReportHistoryRequest;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardRequest;
import lalalabs.pharmacy_crop.business.post.api.dto.request.RegisterAnswerRequest;
import lalalabs.pharmacy_crop.business.post.application.FarmGuardService;
import lalalabs.pharmacy_crop.business.post.domain.ReportReason;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/farm-guards")
@RestController
@RequiredArgsConstructor
public class FarmGuardController {

    private final FarmGuardService service;
    private final FarmGuardService farmGuardService;

    @ApiHeader
    @Operation(summary = "자주 찾는 병충해 목록 조회", description = "자주 찾는 병충해 목록을 조회합니다.")
    @GetMapping("/frequently")
    public ResponseEntity<ApiResponse> getFrequentlyFarmGuards(
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size
    ) {
        List<FarmGuardDto> farmGuardDtoList = farmGuardService.readFrequently(size);

        return ResponseEntity.ok(SuccessResponse.of(farmGuardDtoList));
    }

    @ApiHeader
    @Operation(summary = "병충해 목록 조회", description = "병충해 목록을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<ApiResponse> getFarmGuards(
            @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page,
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size
    ) {
        List<FarmGuardDto> farmGuardDtoList = farmGuardService.read(page, size);

        return ResponseEntity.ok(SuccessResponse.of(farmGuardDtoList));
    }

    @ApiHeader
    @Operation(summary = "병충해 상세 조회", description = "병충해 상세를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFarmGuard(
            @Parameter(name = "id", description = "병충해 ID", required = true) @PathVariable("id") Long id
    ) {
        FarmGuardDetailDto farmGuardDto = farmGuardService.readById(id);

        return ResponseEntity.ok(SuccessResponse.of(farmGuardDto));
    }

    @ApiHeader
    @Operation(summary = "병충해 등록", description = "병충해를 등록합니다.")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createFarmGuard(
            @AuthenticationPrincipal OauthUserDetails userDetails,
            @Parameter(name = "file", description = "이미지 파일", required = true) @RequestPart MultipartFile file,
            @Parameter(name = "content", description = "내용", required = true) @RequestPart String content
    ) {
        CommandFarmGuardRequest request = CommandFarmGuardRequest.builder()
                .content(content)
                .build();

        service.create(userDetails.getUser(), request, file);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "병충해 삭제", description = "병충해를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFarmGuard(
            @AuthenticationPrincipal OauthUserDetails userDetails,
            @Parameter(name = "id", description = "병충해 ID", required = true) @PathVariable("id") Long id
    ) {
        String userId = userDetails.getUser().getId();

        service.delete(userId, id);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @Operation(summary = "병충해 신고", description = "병충해를 신고합니다.")
    @PostMapping("/{id}/report")
    public ResponseEntity<ApiResponse> reportFarmGuard(
            @AuthenticationPrincipal OauthUserDetails userDetails,
            @Parameter(name = "id", description = "병충해 ID", required = true) @PathVariable("id") Long id,
            @Parameter(name = "reportReason", description = "신고 내용", required = true) @RequestParam int reportReason
    ) {
        CommandFarmGuardReportHistoryRequest reportHistoryDto = CommandFarmGuardReportHistoryRequest.builder()
                .farmGuardId(id)
                .userId(userDetails.getUser().getId())
                .content(ReportReason.from(reportReason))
                .build();

        service.report(reportHistoryDto);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    // todo: 관리자 전용 기능
    @ApiHeader
    @Operation(summary = "병충해 답변", description = "병충해에 답변합니다.")
    @PostMapping("/{id}/answers")
    public ResponseEntity<ApiResponse> answerFarmGuard(
            @Parameter(name = "id", description = "병충해 ID", required = true) @PathVariable("id") Long id,
            @Parameter(name = "content", description = "답변 내용", required = true) @RequestBody RegisterAnswerRequest content
    ) {
        farmGuardService.answer(id, content);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    // todo: 관리자 전용 기능
    @ApiHeader
    @Operation(summary = "자주 찾는 병충해 등록 또는 삭제", description = "자주 찾는 병충해를 등록 또는 삭제합니다.")
    @PostMapping("/frequently")
    public ResponseEntity<ApiResponse> createFrequentlyFarmGuard(
            @Parameter(name = "farmGuardId", description = "병충해 ID", required = true) @RequestParam Long farmGuardId
    ) {
        farmGuardService.updateFarmGuardViewedStatus(farmGuardId);

        return ResponseEntity.ok(SuccessResponse.of());
    }
}
