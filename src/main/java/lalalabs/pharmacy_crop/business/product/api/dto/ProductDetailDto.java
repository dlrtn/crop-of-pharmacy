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

    private Integer productBox;

    private String modelName;

    private String productPurpose;

    private String productImage;

    private String company;

    private String mechanism;

    private String itemName;

    private String etc;

    public static ProductDetailDto from(Product product) {
        return ProductDetailDto.builder()
                .productId(product.getProductCode())
                .productName(product.getName())
                .productPrice(product.getNormalPrice())
                .productDiscountPrice(product.getDiscountPrice())
                .productUnit(product.getVolume())
                .productBox(product.getQuantityPerBox() == null ? 1 : product.getQuantityPerBox())
                .modelName(product.getProductCode())
                .productPurpose(product.getCategory().name())
                .productImage(product.getPicture())
                .company(product.getCompany())
                .mechanism(product.getMechanism())
                .itemName(product.getItemName())
                .etc(product.getEtc())
                .build();
    }
}
