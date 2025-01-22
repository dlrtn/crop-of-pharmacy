package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items;

    @Column(name = "original_total_price")
    private Integer originalTotalPrice;

    @Column(name = "discount_total_price")
    private Integer discountTotalPrice;

    public static ShoppingCart emptyCart(String userId) {
        return ShoppingCart.builder()
                .userId(userId)
                .originalTotalPrice(0)
                .discountTotalPrice(0)
                .items(new ArrayList<>())
                .build();
    }

    public void calculateTotalPrice() {
        this.discountTotalPrice = items.stream()
                .mapToInt(ShoppingCartItem::getDiscountTotalPrice)
                .sum();
        this.originalTotalPrice = items.stream()
                .mapToInt(ShoppingCartItem::getOriginalTotalPrice)
                .sum();
    }

    public void addItem(ShoppingCartItem item) {
        if (items.stream().anyMatch(i -> i.getProduct().getProductId().equals(item.getProduct().getProductId()))) {
            item.setShoppingCart(this);
        } else {
            items.add(item);
            item.setShoppingCart(this);
        }
    }

    public void clear() {
        items.clear();
        this.originalTotalPrice = 0;
        this.discountTotalPrice = 0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

