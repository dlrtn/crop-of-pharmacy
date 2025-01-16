package lalalabs.pharmacy_crop.business.product.application;

import lalalabs.pharmacy_crop.business.product.api.dto.OrderDto;
import lalalabs.pharmacy_crop.business.product.domain.Order;
import lalalabs.pharmacy_crop.business.product.domain.ShoppingCart;
import lalalabs.pharmacy_crop.business.product.infrastructure.OrderRepository;
import lalalabs.pharmacy_crop.business.product.infrastructure.ShoppingCartRepository;
import lalalabs.pharmacy_crop.business.user.application.UserQueryService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final UserQueryService userQueryService;

    @Transactional
    public OrderDto createOrder(String userId) {
        // 장바구니 확인
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 비어 있습니다."));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("장바구니에 물품이 없습니다.");
        }

        OauthUser user = userQueryService.findByUserId(userId);

        // 장바구니를 기반으로 주문 생성
        Order order = Order.fromCartAndUser(cart, user);
        orderRepository.save(order);

        // 장바구니 초기화
        cart.clear();
        shoppingCartRepository.save(cart);

        return OrderDto.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());

        List<Order> orders = orderRepository.findAll(pageable).getContent();

        return OrderDto.from(orders);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderDto::from)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }
}
