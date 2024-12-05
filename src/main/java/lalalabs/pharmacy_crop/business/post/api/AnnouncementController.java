package lalalabs.pharmacy_crop.business.post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lalalabs.pharmacy_crop.business.post.api.dto.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.application.AnnouncementService;
import lalalabs.pharmacy_crop.business.post.application.dto.AnnouncementDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "공지사항", description = "공지사항을 생성, 조회, 수정, 삭제합니다.")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {

    private final AnnouncementService service;

    @ApiHeader
    @GetMapping("/announcements")
    public ResponseEntity<ApiResponse> readAnnouncements(@RequestParam int page, @RequestParam int size) {
        List<AnnouncementDto> announcementDtoList = service.read(page, size);

        return ResponseEntity.ok(SuccessResponse.of(announcementDtoList));
    }

    @ApiHeader
    @GetMapping("/announcements/{id}")
    public ResponseEntity<ApiResponse> readAnnouncementById(@PathVariable("id") Long id) {
        AnnouncementDto announcementDto = service.readById(id);

        return ResponseEntity.ok(SuccessResponse.of(announcementDto));
    }

    @ApiHeader
    @Operation(summary = "공지사항 생성", description = "공지사항을 생성합니다.")
    @PostMapping(value = "/announcements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createAnnouncement(
            @AuthenticationPrincipal OauthUser user,
            @RequestPart("file") MultipartFile file,
            @RequestPart CommandAnnouncementRequest request) {
        service.create(user, request, file);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @PutMapping("/announcements/{id}")
    public ResponseEntity<ApiResponse> updateAnnouncement(
            @PathVariable("id") Long id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "request", required = false) CommandAnnouncementRequest request
    ) {
        service.update(id, request, file);

        return ResponseEntity.ok(SuccessResponse.of());
    }

    @ApiHeader
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<ApiResponse> deleteAnnouncement(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok(SuccessResponse.of());
    }
}
