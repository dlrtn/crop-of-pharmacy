package lalalabs.pharmacy_crop.business.post.application.usecase;

import lalalabs.pharmacy_crop.business.post.api.dto.CreateAnnouncementRequest;

public interface AnnouncementCommandServiceInterface {
    void createAnnouncement(Long userId, CreateAnnouncementRequest request);

    // todo 인가 과정 거쳐야하는가?
    void updateAnnouncement(Long announcementId);

    void deleteAnnouncement(Long announcementId);
}
