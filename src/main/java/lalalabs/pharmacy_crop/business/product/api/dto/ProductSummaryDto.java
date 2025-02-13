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

    public static ProductSummaryDto from(Product product) {
        return ProductSummaryDto.builder()
                .productId(product.getProductCode())
                .productName(product.getName())
                .productPrice(product.getNormalPrice())
                .productDiscountPrice(product.getReleasePaymentUnitPrice())
                .build();
    }
}
