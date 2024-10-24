package lalalabs.pharmacy_crop.business.post.api;

import lalalabs.pharmacy_crop.business.post.api.dto.CreateAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.application.usecase.AnnouncementCommandServiceInterface;
import lalalabs.pharmacy_crop.business.post.application.usecase.AnnouncementReadServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AnnouncementController {
    // todo 공지사항 접근에 사용자의 제한을 둘 것인가.

    private final AnnouncementCommandServiceInterface announcementCommandService;
    private final AnnouncementReadServiceInterface announcementReadService;

    @GetMapping("/announcements")
    public void readAnnouncements() {
        announcementReadService.readAnnouncements();
    }

    @GetMapping("/announcements/{id}")
    public void readAnnouncementById(@PathVariable("id") Long id) {
        announcementReadService.readAnnouncementById(id);
    }

    @PostMapping("/announcements")
    public void createAnnouncement(Long userId, @RequestBody CreateAnnouncementRequest request) {
        announcementCommandService.createAnnouncement(userId, request);
    }

    @PutMapping("/announcements/{id}")
    public void updateAnnouncement(@PathVariable("id") Long id) {
        announcementCommandService.updateAnnouncement(id);
    }

    @DeleteMapping("/announcements/{id}")
    public void deleteAnnouncement(@PathVariable("id") Long id) {
        announcementCommandService.deleteAnnouncement(id);
    }
}
