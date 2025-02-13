package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Integer discountPrice;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "total_price")
    private Integer discountTotalPrice;

    @Column(name = "original_total_price")
    private Integer originalTotalPrice;

    @Column(name = "unit", length = 10)
    private String unit;

    @Column(name = "pack", length = 10)
    private String pack;

    public void updateQuantity(Integer quantity) {
        this.quantity += quantity;
        this.discountTotalPrice = this.discountPrice * this.quantity;
        this.originalTotalPrice = this.originalPrice * this.quantity;
    }

    public static ShoppingCartItem from(ShoppingCart shoppingCart, Product product) {
        return ShoppingCartItem.builder()
                .shoppingCart(shoppingCart)
                .product(product)
                .quantity(0)
                .discountPrice(product.getReleasePaymentUnitPrice())
                .originalPrice(product.getNormalPrice())
                .originalTotalPrice(0)
                .discountTotalPrice(0)
                .unit(product.getVolume())
                .pack(product.getPack())
                .build();
    }
}
