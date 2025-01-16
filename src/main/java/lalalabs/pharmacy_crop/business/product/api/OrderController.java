package lalalabs.pharmacy_crop.business.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.product.api.dto.OrderDto;
import lalalabs.pharmacy_crop.business.product.application.OrderService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "주문", description = "주문 관리를 제공합니다.")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiHeader
    @PostMapping
    @Operation(summary = "주문 생성", description = "장바구니의 내용을 주문으로 생성합니다.")
    public ResponseEntity<ApiResponse> createOrder(@AuthenticationPrincipal OauthUserDetails user) {
        OrderDto orderDto = orderService.createOrder(user.getUserId());

        return ResponseEntity.ok(SuccessResponse.of(orderDto));
    }

    @ApiHeader
    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "모든 주문을 조회합니다.")
    public ResponseEntity<ApiResponse> getOrders(int page, int size) {
        List<OrderDto> orders = orderService.getOrders(page, size);

        return ResponseEntity.ok(SuccessResponse.of(orders));
    }

    @ApiHeader
    @GetMapping("/{id}")
    @Operation(summary = "주문 상세 조회", description = "특정 주문을 조회합니다.")
    public ResponseEntity<ApiResponse> getOrder(@Parameter(name = "id", description = "주문 ID", required = true) @PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);

        return ResponseEntity.ok(SuccessResponse.of(order));
    }
}
