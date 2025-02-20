package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductSummaryDto {

    private String productId;

    private String productName;

    private Integer productPrice;

    private Integer productDiscountPrice;

    private String company;

    private String unit;

    private Integer box;

    private String productImage;

    public static ProductSummaryDto from(Product product) {
        return ProductSummaryDto.builder()
                .productId(product.getProductCode())
                .productName(product.getName())
                .productPrice(product.getNormalPrice())
                .productDiscountPrice(product.getDiscountPrice())
                .company(product.getCompany())
                .unit(product.getVolume())
                .box(product.getQuantityPerBox() == null ? 1 : product.getQuantityPerBox())
                .productImage(product.getPicture() != null ? "http://1.234.83.196:8080" + product.getPicture().substring(4) : null)
                .build();
    }
}
