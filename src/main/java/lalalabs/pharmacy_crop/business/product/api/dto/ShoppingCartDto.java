package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.ShoppingCart;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShoppingCartDto {

    private String userId;

    private List<ShoppingCartItemDto> items;

    private Integer originalTotalPrice;

    private Integer discountTotalPrice;

    public static ShoppingCartDto from(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
                .userId(shoppingCart.getUserId())
                .items(shoppingCart.getItems().stream().map(ShoppingCartItemDto::from).toList())
                .originalTotalPrice(shoppingCart.getOriginalTotalPrice())
                .discountTotalPrice(shoppingCart.getDiscountTotalPrice())
                .build();
    }
}
