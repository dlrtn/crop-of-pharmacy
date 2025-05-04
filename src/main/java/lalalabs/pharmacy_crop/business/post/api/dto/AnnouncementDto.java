package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.Announcement;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.domain.Product;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementDto {

    private Long id;

    private String title;

    private String content;

    private String picture;

    private String createdAt;

    private List<ProductDetailDto> products;

    public static AnnouncementDto from(Announcement announcement) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .picture(announcement.getPicturePath() != null ? String.format("http://1.234.83.196:8080%s", announcement.getPicturePath().substring(4)) : null)
                .createdAt(TimeUtils.convertToDateFormat(announcement.getCreatedDate()))
                .products(null)
                .build();
    }

    public static AnnouncementDto fromDomain(Announcement announcement, List<Product> products) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .picture(announcement.getPicturePath() != null ? String.format("http://1.234.83.196:8080%s", announcement.getPicturePath().substring(4)) : null)
                .createdAt(TimeUtils.convertToDateFormat(announcement.getCreatedDate()))
                .products(products.stream()
                        .map(ProductDetailDto::from)
                        .toList())
                .build();
    }
}
