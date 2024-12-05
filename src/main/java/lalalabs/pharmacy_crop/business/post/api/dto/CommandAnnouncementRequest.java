package lalalabs.pharmacy_crop.business.post.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommandAnnouncementRequest {
    String title;
    String content;
}
