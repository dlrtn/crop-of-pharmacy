package lalalabs.pharmacy_crop.business.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardReportHistoryRequest;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmGuardReportHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long farmGuardId;

    @Column
    private String userId;

    @Column
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
