package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardReportHistoryRequest;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "farm_guard_report_history")
public class FarmGuardReportHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "farm_guard_id")
    private Long farmGuardId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "content")
    @Enumerated
    private ReportReason content;

    public static FarmGuardReportHistory create(CommandFarmGuardReportHistoryRequest reportHistoryDto) {
        return FarmGuardReportHistory.builder()
                .farmGuardId(reportHistoryDto.getFarmGuardId())
                .userId(reportHistoryDto.getUserId())
                .content(reportHistoryDto.getContent())
                .build();
    }
}
