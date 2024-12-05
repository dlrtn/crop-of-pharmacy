package lalalabs.pharmacy_crop.business.post.application;

import jakarta.transaction.Transactional;
import java.util.List;
import lalalabs.pharmacy_crop.business.post.api.dto.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.application.dto.AnnouncementDto;
import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.AnnouncementRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.upload.LocalFileUploader;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.file.DirectoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final LocalFileUploader localFileUploader;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    public void create(OauthUser userId, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = Announcement.builder()
                .userId(userId.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .picturePath(localFileUploader.upload(file, DirectoryType.Announcement))
                .build();

        announcementRepository.save(announcement);
    }

    public void update(Long announcementId, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

        if (request != null) {
            announcement.update(request.getTitle(), request.getContent());
        }

        if (file != null) {
            announcement.updatePicturePath(localFileUploader.upload(file, DirectoryType.Announcement));
        }

        announcementRepository.save(announcement);
    }

    @Transactional
    public void delete(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public List<AnnouncementDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return announcementRepository.findAll(pageable)
                .stream()
                .map(AnnouncementDto::fromDomain)
                .toList();
    }

    public AnnouncementDto readById(Long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(AnnouncementDto::fromDomain)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }
}
