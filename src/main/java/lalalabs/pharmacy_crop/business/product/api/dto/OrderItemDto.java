package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDto {

    private String productName;

    private Integer quantity;

    private Integer discountPrice;

    private Integer originalPrice;

    private Integer discountTotalPrice;

    private Integer originalTotalPrice;

    private String productUnit;

    private String productPack;

    public static OrderItemDto from(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .discountPrice(orderItem.getDiscountPrice())
                .originalPrice(orderItem.getOriginalPrice())
                .discountTotalPrice(orderItem.getDiscountTotalPrice())
                .originalTotalPrice(orderItem.getOriginalTotalPrice())
                .productUnit(orderItem.getProductUnit())
                .productPack(orderItem.getProductPack())
                .build();
    }
}
