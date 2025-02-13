package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.ShoppingCart;
import lalalabs.pharmacy_crop.business.product.domain.ShoppingCartItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShoppingCartDto {

    private String userId;

    private List<ShoppingCartItemDto> items;

    private Integer itemCount;

    private Integer originalTotalPrice;

    private Integer discountTotalPrice;

    public static ShoppingCartDto from(ShoppingCart shoppingCart) {
        int itemCount = shoppingCart.getItems().stream().mapToInt(ShoppingCartItem::getQuantity).sum();

        return ShoppingCartDto.builder()
                .userId(shoppingCart.getUserId())
                .items(shoppingCart.getItems().stream().map(ShoppingCartItemDto::from).toList())
                .itemCount(itemCount)
                .originalTotalPrice(shoppingCart.getOriginalTotalPrice())
                .discountTotalPrice(shoppingCart.getDiscountTotalPrice())
                .build();
    }
}
