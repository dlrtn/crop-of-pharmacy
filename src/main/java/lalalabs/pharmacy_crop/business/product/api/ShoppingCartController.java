package lalalabs.pharmacy_crop.business.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.product.api.dto.ShoppingCartDto;
import lalalabs.pharmacy_crop.business.product.application.ShoppingCartService;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니", description = "장바구니 관리를 제공합니다.")
@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @ApiHeader
    @Operation(summary = "장바구니 조회", description = "사용자의 장바구니를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse> getShoppingCart(@AuthenticationPrincipal OauthUserDetails user) {
        ShoppingCartDto shoppingCartDto = shoppingCartService.getCart(user.getUserId());
        return ResponseEntity.ok(SuccessResponse.of(shoppingCartDto));
    }

    @ApiHeader
    @Operation(summary = "장바구니 담긴 상품 갯수 조회", description = "사용자의 장바구니에 담긴 상품 갯수를 조회합니다.")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getShoppingCartItemCount(@AuthenticationPrincipal OauthUserDetails user) {
        int serviceCartItemCount = shoppingCartService.getCartItemCount(user.getUserId());

        return ResponseEntity.ok(SuccessResponse.of(serviceCartItemCount));
    }

    @ApiHeader
    @Operation(summary = "장바구니에 상품 추가", description = "사용자의 장바구니에 상품을 추가합니다.")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@AuthenticationPrincipal OauthUserDetails user,
            @Parameter(name = "productId", description = "상품 ID", required = true) @RequestParam String productId,
            @Parameter(name = "quantity", description = "수량", required = true) @RequestParam int quantity) {
        ShoppingCartDto updatedCart = shoppingCartService.addToCart(user.getUserId(), productId, quantity);
        return ResponseEntity.ok(SuccessResponse.of(updatedCart));
    }

    @ApiHeader
    @Operation(summary = "장바구니에서 상품 제거", description = "사용자의 장바구니에서 특정 상품을 제거합니다.")
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeFromCart(@AuthenticationPrincipal OauthUserDetails user,
            @Parameter(name = "productId", description = "상품 ID", required = true) @RequestParam String productId) {
        shoppingCartService.removeFromCart(user.getUserId(), productId);
        return ResponseEntity.ok(SuccessResponse.of("상품이 장바구니에서 제거되었습니다."));
    }

    @ApiHeader
    @Operation(summary = "장바구니 비우기", description = "사용자의 장바구니를 초기화합니다.")
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(@AuthenticationPrincipal OauthUserDetails user) {
        shoppingCartService.clearCart(user.getUserId());

        return ResponseEntity.ok(SuccessResponse.of("장바구니가 초기화되었습니다."));
    }
}
