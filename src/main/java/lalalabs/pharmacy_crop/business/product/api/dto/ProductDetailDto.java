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

    private String productUnitGub;

    private String productBox;

    private String productBoxGub;

    private String modelName;

    public static ProductDetailDto from(Product product) {
        String modelName = product.getModelName();

        return ProductDetailDto.builder()
                .productId(product.getFPcode())
                .productName(product.getFname())
                .productPrice(product.getFtdanga())
                .productDiscountPrice(product.getFcdanga())
                .productUnit(product.getFunit())
                .productUnitGub(product.getFunitGubDescription())
                .productBox(product.getFbox())
                .productBoxGub(product.getFboxGubDescription())
                .modelName(modelName)
                .build();
    }
}
