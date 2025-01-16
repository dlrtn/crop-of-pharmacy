package lalalabs.pharmacy_crop.business.product.application;

import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductSummaryDto;
import lalalabs.pharmacy_crop.business.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductSummaryDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId").ascending());

        return productRepository.findAll(pageable).stream()
                .map(ProductSummaryDto::from)
                .toList();
    }

    public List<ProductSummaryDto> readMonthlyBest(int page, int size) {
        // TODO: Implement this method
        return read(page, size);
    }

    public List<ProductSummaryDto> readByCategory(String category, int page, int size) {
        // Todo: Implement this method
        return read(page, size);
    }

    public ProductDetailDto readById(String productId) {
        return productRepository.findById(productId)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }
}
