package lalalabs.pharmacy_crop.business.post.application;

import jakarta.transaction.Transactional;
import java.util.List;
import lalalabs.pharmacy_crop.business.post.api.dto.FarmGuardDto;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardReportHistoryRequest;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandFarmGuardRequest;
import lalalabs.pharmacy_crop.business.post.api.dto.request.RegisterAnswerRequest;
import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import lalalabs.pharmacy_crop.business.post.domain.FarmGuardReportHistory;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.FarmGuardAnswerRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.FarmGuardReportRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.FarmGuardRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class FarmGuardService {

    private final LocalFileUploader localFileUploader;
    private final FarmGuardRepository guardRepository;
    private final FarmGuardReportRepository reportRepository;
    private final FarmGuardAnswerRepository farmGuardAnswerRepository;

    @Transactional
    public void create(OauthUser user, CommandFarmGuardRequest request, MultipartFile file) {
        String picturePath = localFileUploader.upload(file, DirectoryType.FARM_GUARD);

        FarmGuard farmGuard = FarmGuard.create(request, user, picturePath);

        guardRepository.save(farmGuard);
    }

    @Transactional
    public void delete(String userId, Long announcementId) {
        FarmGuard farmGuard = guardRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));

        if (!farmGuard.isOwner(userId)) {
            throw new IllegalArgumentException("해당 병충해 찾기 게시글을 삭제할 권한이 없습니다.");
        }

        guardRepository.deleteById(announcementId);
    }

    public List<FarmGuardDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        List<FarmGuard> farmGuards = guardRepository.findAll(pageable).getContent().stream().toList();

        return farmGuards.stream().map(farmGuard -> {
            boolean isReported = reportRepository.existsById(farmGuard.getId());
            return FarmGuardDto.fromDomain(farmGuard, isReported);
        }).toList();
    }

    public FarmGuardDto readById(Long announcementId) {
        return guardRepository.findById(announcementId).map(farmGuard -> {
            boolean isReported = reportRepository.existsById(farmGuard.getId());
            return FarmGuardDto.fromDomain(farmGuard, isReported);
        }).orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }

    public void report(CommandFarmGuardReportHistoryRequest reportHistoryDto) {
        FarmGuard farmGuard = guardRepository.findById(reportHistoryDto.getFarmGuardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));

        if (farmGuard.isOwner(reportHistoryDto.getUserId())) {
            throw new IllegalArgumentException("자신의 병충해 찾기 게시글은 신고할 수 없습니다.");
        }

        FarmGuardReportHistory history = FarmGuardReportHistory.create(reportHistoryDto);

        reportRepository.save(history);
    }

    public void answer(Long id, RegisterAnswerRequest content) {
        FarmGuard farmGuard = guardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));

        FarmGuardAnswer answer = FarmGuardAnswer.builder()
                .farmGuardId(id)
                .userId(farmGuard.getUserId())
                .content(content.getContent())
                .build();

        farmGuardAnswerRepository.save(answer);
    }
}
