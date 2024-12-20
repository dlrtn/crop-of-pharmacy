package lalalabs.pharmacy_crop.business.post.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommandFarmGuardRequest {
    String content;
}
