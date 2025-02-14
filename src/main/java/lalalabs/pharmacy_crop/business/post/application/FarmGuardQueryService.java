package lalalabs.pharmacy_crop.business.post.application;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.FarmGuardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FarmGuardQueryService {

    private final FarmGuardRepository guardRepository;

    @Transactional(readOnly = true)
    public FarmGuard getOrElseThrow(Long farmGuardId) {
        return guardRepository.findById(farmGuardId).orElseThrow(() -> new IllegalArgumentException("해당 병충해 찾기 게시글이 존재하지 않습니다."));
    }

    public List<FarmGuard> findAll(Pageable pageable) {
        return guardRepository.findAll(pageable).getContent();
    }
}
