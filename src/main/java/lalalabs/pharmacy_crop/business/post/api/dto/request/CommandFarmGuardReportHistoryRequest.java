package lalalabs.pharmacy_crop.business.post.api.dto.request;

import lalalabs.pharmacy_crop.business.post.domain.ReportReason;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommandFarmGuardReportHistoryRequest {

    private Long id;

    private Long farmGuardId;

    private String userId;
    
    private ReportReason content;
}
