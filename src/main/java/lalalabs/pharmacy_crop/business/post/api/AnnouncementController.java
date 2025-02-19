package lalalabs.pharmacy_crop.business.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.application.AnnouncementService;
import lalalabs.pharmacy_crop.business.post.api.dto.AnnouncementDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.security.OnlyAdmin;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "공지사항", description = "공지사항을 생성, 조회, 수정, 삭제합니다.")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {

    private final AnnouncementService service;

    @ApiHeader
    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    @GetMapping("/announcements")
    public ResponseEntity<ApiResponse> read(
            @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page,
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size
    ) {
        List<AnnouncementDto> announcementDtoList = service.read(page, size);

        return ResponseEntity.ok(SuccessResponse.of(announcementDtoList));
    }

    @ApiHeader
    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세를 조회합니다.")
    @GetMapping("/announcements/{id}")
    public ResponseEntity<ApiResponse> readById(
            @Parameter(name = "id", description = "공지사항 ID", required = true) @PathVariable("id") Long id
    ) {
        AnnouncementDto announcementDto = service.readById(id);

        return ResponseEntity.ok(SuccessResponse.of(announcementDto));
    }

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    @PostMapping(value = "/announcements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> create(
            @AuthenticationPrincipal OauthUserDetails user,
            @Parameter(name = "file", description = "파일", required = true) @RequestPart("file") MultipartFile file,
            @Parameter(name = "title", description = "제목", required = true) @RequestPart("title") String title,
            @Parameter(name = "content", description = "내용", required = true) @RequestPart("content") String content
    ) {
        CommandAnnouncementRequest request = CommandAnnouncementRequest.builder()
                .title(title)
                .content(content)
                .build();

        service.create(user.getUser(), request, file);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    @PutMapping(value = "/announcements/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> update(
            @Parameter(name = "id", description = "공지사항 ID", required = true) @PathVariable("id") Long id,
            @Parameter(name = "file", description = "파일") @RequestPart(value = "file", required = false) MultipartFile file,
            @Parameter(name = "title", description = "제목", required = true) @RequestPart("title") String title,
            @Parameter(name = "content", description = "내용", required = true) @RequestPart("content") String content
    ) {
        CommandAnnouncementRequest request = CommandAnnouncementRequest.builder()
                .title(title)
                .content(content)
                .build();

        service.update(id, request, file);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<ApiResponse> delete(
            @Parameter(name = "id", description = "공지사항 ID", required = true) @PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok(SuccessResponse.of());
    }
}
