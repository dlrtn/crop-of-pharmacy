package lalalabs.pharmacy_crop.business.product.application;

import lalalabs.pharmacy_crop.business.product.api.dto.ShoppingCartDto;
import lalalabs.pharmacy_crop.business.product.domain.Product;
import lalalabs.pharmacy_crop.business.product.domain.ShoppingCart;
import lalalabs.pharmacy_crop.business.product.domain.ShoppingCartItem;
import lalalabs.pharmacy_crop.business.product.infrastructure.ProductRepository;
import lalalabs.pharmacy_crop.business.product.infrastructure.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ShoppingCartDto addToCart(String userId, String productCode, int quantity) {
        // 장바구니 가져오기 (존재하지 않으면 생성)
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> shoppingCartRepository.save(ShoppingCart.emptyCart(userId)));

        // 상품 가져오기
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 장바구니에 상품 추가
        ShoppingCartItem item = shoppingCart.getItems().stream()
                .filter(i -> i.getProduct().getProductCode().equals(productCode))
                .findFirst()
                .orElse(ShoppingCartItem.from(shoppingCart, product));

        // 수량 및 총 금액 업데이트
        item.updateQuantity(quantity);
        shoppingCart.addItem(item);

        // 장바구니 총 금액 업데이트
        shoppingCart.calculateTotalPrice();

        return ShoppingCartDto.from(shoppingCart);
    }

    @Transactional(readOnly = true)
    public ShoppingCartDto getCart(String userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElse(ShoppingCart.emptyCart(userId));

        return ShoppingCartDto.from(shoppingCart);
    }

    @Transactional(readOnly = true)
    public int getCartItemCount(String userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElse(ShoppingCart.emptyCart(userId));

        return ShoppingCartDto.from(shoppingCart).getItemCount();
    }

    @Transactional
    public void removeFromCart(String userId, String productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        shoppingCart.getItems().removeIf(item -> item.getProduct().getProductCode().equals(productId));
        shoppingCart.calculateTotalPrice();
        shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void clearCart(String userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        shoppingCart.clear();
        shoppingCartRepository.save(shoppingCart);
    }
}
