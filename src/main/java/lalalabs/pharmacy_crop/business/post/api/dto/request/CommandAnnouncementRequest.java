package lalalabs.pharmacy_crop.business.post.api.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommandAnnouncementRequest {
    String title;
    String content;
    List<String> productIds;
}
