package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.BaseDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "original_total_price", nullable = false)
    private Integer originalTotalPrice;

    @Column(name = "discount_total_price", nullable = false)
    private Integer discountTotalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public static Order fromCartAndUser(ShoppingCart cart, OauthUser user) {
        List<OrderItem> orderItems = OrderItem.fromCartItems(cart.getItems());
        Order order = Order.builder()
                .userId(user.getId())
                .userName(user.getNickname())
                .originalTotalPrice(cart.getOriginalTotalPrice())
                .discountTotalPrice(cart.getDiscountTotalPrice())
                .items(orderItems)
                .orderDate(BaseDateTimeUtils.getNow())
                .status(OrderStatus.PENDING)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return order;
    }
}
