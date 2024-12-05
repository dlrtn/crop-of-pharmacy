package lalalabs.pharmacy_crop.business.post.api.dto;

import lombok.Builder;

@Builder
public record CommandAnnouncementRequest(
        String title,
        String content
) {
}
