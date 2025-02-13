package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDetailDto {

    private String productId;

    private String productName;

    private Integer productPrice;

    private Integer productDiscountPrice;

    private String productUnit;

    private String productBox;

    private String productBoxGub;

    private String modelName;

    private String productCategory;

    public static ProductDetailDto from(Product product) {
        return ProductDetailDto.builder()
                .productId(product.getProductCode())
                .productName(product.getName())
                .productPrice(product.getNormalPrice())
                .productDiscountPrice(product.getReleasePaymentUnitPrice())
                .productUnit(product.getVolume())
                .productBox(product.getQuantityPerBox())
                .productBoxGub(product.getBoxUnitDescription())
                .modelName(product.getProductCode())
                .productCategory(product.getPurpose().name())
                .build();
    }
}
