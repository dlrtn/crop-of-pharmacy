package lalalabs.pharmacy_crop.business.post.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.post.api.dto.AnnouncementDto;
import lalalabs.pharmacy_crop.business.post.api.dto.request.CommandAnnouncementRequest;
import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.business.post.domain.AnnouncementProduct;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.AnnouncementProductRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.repository.AnnouncementRepository;
import lalalabs.pharmacy_crop.business.post.infrastructure.upload.LocalFileUploader;
import lalalabs.pharmacy_crop.business.product.domain.Product;
import lalalabs.pharmacy_crop.business.product.infrastructure.ProductRepository;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.common.file.DirectoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final LocalFileUploader localFileUploader;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementProductRepository announcementProductRepository;
    private final ProductRepository productRepository;
    private final AnnouncementPushNotificationSender pushNotificationSender;

    @Transactional
    public void create(OauthUser user, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = Announcement.create(user.getId(), request);

        if (file != null) {
            announcement.updatePicturePath(localFileUploader.upload(file, DirectoryType.ANNOUNCEMENT));
        }

        // pushNotificationSender.sendAnnouncementPushNotification();

        Announcement newAnnouncement = announcementRepository.save(announcement);

        if (request.getProductIds() != null) {
            for (String productId : request.getProductIds()) {
                log.info("productId: {}", productId);
                AnnouncementProduct announcementProduct = AnnouncementProduct.builder()
                        .announcementId(newAnnouncement.getId())
                        .productId(productId)
                        .build();

                announcementProductRepository.save(announcementProduct);
            }
        }
    }

    public void update(Long announcementId, CommandAnnouncementRequest request, MultipartFile file) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

        if (request != null) {
            announcement.update(request);
        }

        if (file != null) {
            announcement.updatePicturePath(localFileUploader.upload(file, DirectoryType.ANNOUNCEMENT));
        }

        announcementRepository.save(announcement);

        if (Objects.requireNonNull(request).getProductIds() != null) {
            for (String productId : request.getProductIds()) {
                AnnouncementProduct announcementProduct = AnnouncementProduct.builder()
                        .announcementId(announcement.getId())
                        .productId(productId)
                        .build();

                announcementProductRepository.save(announcementProduct);
            }
        }
    }


    @Transactional
    public void delete(Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    public List<AnnouncementDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return announcementRepository.findAll(pageable).getContent().stream().map(AnnouncementDto::from).toList();
    }

    public AnnouncementDto readById(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(
                () -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

        List<AnnouncementProduct> announcementProducts = announcementProductRepository.findByAnnouncementId(announcementId);

        List<Product> products = productRepository.findAllByProductCodeIn((
                announcementProducts.stream()
                        .map(AnnouncementProduct::getProductId)
                        .toList()
        ));

        return AnnouncementDto.fromDomain(announcement, products);


    }
}
