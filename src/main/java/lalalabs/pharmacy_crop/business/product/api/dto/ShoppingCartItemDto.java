package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.ShoppingCartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShoppingCartItemDto {

    private String productId;

    private String productName;

    private Integer quantity;

    private String unit;

    private String boxUnit;

    private Integer originalTotalPrice;

    private Integer discountTotalPrice;

    public static ShoppingCartItemDto from(ShoppingCartItem item) {
        return ShoppingCartItemDto.builder()
                .productId(item.getProduct().getFPcode())
                .productName(item.getProduct().getFname())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .boxUnit(item.getProduct().getPack())
                .originalTotalPrice(item.getOriginalTotalPrice())
                .discountTotalPrice(item.getDiscountTotalPrice())
                .build();
    }
}
