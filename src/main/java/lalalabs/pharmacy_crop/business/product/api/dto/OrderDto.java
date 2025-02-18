package lalalabs.pharmacy_crop.business.product.api.dto;

import lalalabs.pharmacy_crop.business.product.domain.Order;
import lalalabs.pharmacy_crop.business.product.domain.OrderStatus;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDto {

    private Long orderId;

    private String nickname;

    private Integer originalTotalPrice;

    private Integer discountTotalPrice;

    private OrderStatus status;

    private LocalDateTime orderDate;

    private List<OrderItemDto> items;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .nickname(order.getUserName())
                .originalTotalPrice(order.getOriginalTotalPrice())
                .discountTotalPrice(order.getDiscountTotalPrice())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .items(order.getItems().stream()
                        .map(OrderItemDto::from)
                        .toList())
                .build();
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderDto.builder()
                        .orderId(order.getId())
                        .nickname(order.getUserName())
                        .originalTotalPrice(order.getOriginalTotalPrice())
                        .discountTotalPrice(order.getDiscountTotalPrice())
                        .status(order.getStatus())
                        .orderDate(order.getOrderDate())
                        .items(order.getItems().stream()
                                .map(OrderItemDto::from)
                                .toList())
                        .build())
                .toList();
    }
}
