package lalalabs.pharmacy_crop.business.post.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.post.api.dto.AnnouncementDto;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.AnnouncementRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.upload.LocalFileUploader;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.file.DirectoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final LocalFileUploader localFileUploader;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementPushNotificationSender pushNotificationSender;

    @Transactional
    public void create(OauthUser user, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = Announcement.create(user.getId(), request);

        if (file != null) {
            announcement.updatePicturePath(localFileUploader.upload(file, DirectoryType.ANNOUNCEMENT));
        }

        pushNotificationSender.sendAnnouncementPushNotification();

        announcementRepository.save(announcement);
    }

    public void update(Long announcementId, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

        if (request != null) {
            announcement.update(request);
        }

        if (file != null) {
            announcement.updatePicturePath(localFileUploader.upload(file, DirectoryType.ANNOUNCEMENT));
        }

        announcementRepository.save(announcement);
    }


    @Transactional
    public void delete(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public List<AnnouncementDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return announcementRepository.findAll(pageable).getContent().stream().map(AnnouncementDto::fromDomain).toList();
    }

    public AnnouncementDto readById(Long announcementId) {
        return announcementRepository.findById(announcementId).map(AnnouncementDto::fromDomain)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }
}
