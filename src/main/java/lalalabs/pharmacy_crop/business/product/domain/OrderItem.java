package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_unit", nullable = false)
    private String productUnit;

    @Column(name = "product_pack", nullable = false)
    private String productPack;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "original_price", nullable = false)
    private Integer originalPrice;

    @Column(name = "discount_price", nullable = false)
    private Integer discountPrice;

    @Column(name = "discount_total_price", nullable = false)
    private Integer discountTotalPrice;

    @Column(name = "original_total_price", nullable = false)
    private Integer originalTotalPrice;

    public static OrderItem fromCartItem(ShoppingCartItem cartItem) {
        return OrderItem.builder()
                .productId(cartItem.getProduct().getFPcode())
                .productName(cartItem.getProduct().getFname())
                .productUnit(cartItem.getUnit())
                .productPack(cartItem.getPack())
                .quantity(cartItem.getQuantity())
                .originalPrice(cartItem.getOriginalPrice())
                .discountPrice(cartItem.getDiscountPrice())
                .originalTotalPrice(cartItem.getOriginalTotalPrice())
                .discountTotalPrice(cartItem.getDiscountTotalPrice())
                .build();
    }

    public static List<OrderItem> fromCartItems(List<ShoppingCartItem> items) {
        return items.stream()
                .map(OrderItem::fromCartItem)
                .toList();
    }
}
