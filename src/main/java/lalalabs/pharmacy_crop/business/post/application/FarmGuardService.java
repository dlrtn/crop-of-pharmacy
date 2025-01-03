package lalalabs.pharmacy_crop.business.post.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.post.api.dto.FarmGuardDetailDto;
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
import lalalabs.pharmacy_crop.business.user.application.UserQueryService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.file.DirectoryType;
import lalalabs.pharmacy_crop.common.push_notification.application.PushNotificationService;
import lalalabs.pharmacy_crop.common.push_notification.domain.model.PushNotificationBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FarmGuardService {

    private final LocalFileUploader localFileUploader;
    private final PushNotificationService pushNotificationService;
    private final FarmGuardRepository guardRepository;
    private final FarmGuardReportRepository reportRepository;
    private final FarmGuardAnswerRepository farmGuardAnswerRepository;
    private final UserQueryService userQueryService;

    @Transactional
    public void create(OauthUser user, CommandFarmGuardRequest request, MultipartFile file) {
        String picturePath = localFileUploader.upload(file, DirectoryType.FARM_GUARD);

        FarmGuard farmGuard = FarmGuard.create(request, user, picturePath);

        guardRepository.save(farmGuard);
    }

    @Transactional
    public void delete(String userId, Long farmGuardId) {
        FarmGuard farmGuard = getOrElseThrow(farmGuardId);

        if (!farmGuard.isOwner(userId)) {
            throw new IllegalArgumentException("해당 병충해 찾기 게시글을 삭제할 권한이 없습니다.");
        }

        guardRepository.deleteById(farmGuardId);
    }

    private FarmGuard getOrElseThrow(Long farmGuardId) {
        return guardRepository.findById(farmGuardId).orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));
    }

    public List<FarmGuardDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        List<FarmGuard> farmGuards = guardRepository.findAll(pageable).getContent().stream().toList();

        return farmGuards.stream().map(this::getFarmGuardDto).toList();
    }

    public FarmGuardDetailDto readById(Long farmGuardId) {
        return guardRepository.findById(farmGuardId).map(this::getFarmGuardDetailDto).orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));
    }

    private FarmGuardDto getFarmGuardDto(FarmGuard farmGuard) {
        boolean isReported = reportRepository.existsById(farmGuard.getId());
        boolean isAnswered = farmGuardAnswerRepository.existsFarmGuardAnswerByFarmGuardId(farmGuard.getId());
        OauthUser user = userQueryService.findByUserId(farmGuard.getUserId());

        return FarmGuardDto.fromDomain(farmGuard, isReported, isAnswered, user);
    }

    private FarmGuardDetailDto getFarmGuardDetailDto(FarmGuard farmGuard) {
        OauthUser user = userQueryService.findByUserId(farmGuard.getUserId());

        if (farmGuardAnswerRepository.existsFarmGuardAnswerByFarmGuardId(farmGuard.getId())) {
            FarmGuardAnswer answer = farmGuardAnswerRepository.findFarmGuardAnswerByFarmGuardId(farmGuard.getId());
            return FarmGuardDetailDto.fromDomain(farmGuard, answer, user);
        }
        return FarmGuardDetailDto.fromDomain(farmGuard, user);
    }

    public void report(CommandFarmGuardReportHistoryRequest reportHistoryDto) {
        FarmGuard farmGuard = getOrElseThrow(reportHistoryDto.getFarmGuardId());

        if (farmGuard.isOwner(reportHistoryDto.getUserId())) {
            throw new IllegalArgumentException("자신의 병충해 찾기 게시글은 신고할 수 없습니다.");
        }

        FarmGuardReportHistory history = FarmGuardReportHistory.create(reportHistoryDto);

        reportRepository.save(history);
    }

    public void answer(Long farmGuardId, RegisterAnswerRequest content) {
        FarmGuard farmGuard = getOrElseThrow(farmGuardId);

        FarmGuardAnswer answer = FarmGuardAnswer.builder().farmGuardId(farmGuardId).userId(farmGuard.getUserId()).content(content.getContent()).productId(content.getProductId()).build();

        farmGuardAnswerRepository.save(answer);

        if (pushNotificationService.isAgreePushNotification(farmGuard.getUserId())) {
            PushNotificationBody notification = PushNotificationBody.builder().title("병충해 찾기 게시글에 답변이 등록되었습니다.").body("병충해 찾기 게시글에 답변이 등록되었습니다.").build();

            pushNotificationService.send(answer.getUserId(), notification);
        }
    }

    public List<FarmGuardDto> readFrequently(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());

        List<FarmGuard> farmGuards = guardRepository.findAllByOftenViewed(true, pageable);

        return farmGuards.stream().map(this::getFarmGuardDto).toList();
    }

    public void updateFarmGuardOftenViewedStatus(Long farmGuardId) {
        FarmGuard farmGuard = getOrElseThrow(farmGuardId);

        farmGuard.updateOftenViewedStatus();
    }
}
