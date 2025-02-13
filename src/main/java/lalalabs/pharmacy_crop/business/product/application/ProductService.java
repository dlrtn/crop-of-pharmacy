package lalalabs.pharmacy_crop.business.product.application;

import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductSummaryDto;
import lalalabs.pharmacy_crop.business.product.domain.ProductCategory;
import lalalabs.pharmacy_crop.business.product.domain.ProductInquiryCount;
import lalalabs.pharmacy_crop.business.product.infrastructure.ProductInquiryCountRepository;
import lalalabs.pharmacy_crop.business.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInquiryCountRepository productInquiryCountRepository;

    public List<ProductSummaryDto> read(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productCode").ascending());

        return productRepository.findAllByNormalPriceIsNotNull(pageable).stream()
                .map(ProductSummaryDto::from)
                .toList();
    }

    public List<ProductSummaryDto> readMonthlyBest() {
        List<ProductInquiryCount> productInquiryCounts = productInquiryCountRepository.findTop10ByOrderByInquiryCountDesc();

        if (!productInquiryCounts.isEmpty()) {
            return productInquiryCounts.stream()
                    .map(productInquiryCount -> productRepository.findById(productInquiryCount.getProductCode()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(ProductSummaryDto::from)
                    .toList();
        }

        return List.of();
    }

    public List<ProductSummaryDto> readByCategory(ProductCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productCode").ascending());

        if (category == ProductCategory.기타) {
            return productRepository.findByCategoryNotInAndNormalPriceIsNotNull(List.of(ProductCategory.농약, ProductCategory.농자재, ProductCategory.방역품, ProductCategory.비료), pageable).stream()
                    .map(ProductSummaryDto::from)
                    .toList();
        }

        return productRepository.findByCategoryAndNormalPriceIsNotNull(category, pageable).stream()
                .map(ProductSummaryDto::from)
                .toList();
    }

    public ProductDetailDto readById(String productId) {
        return productRepository.findByProductCode(productId)
                .map(ProductDetailDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }

    public List<ProductSummaryDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productCode").ascending());

        return productRepository.findByNameContaining(keyword, pageable).stream()
                .map(ProductSummaryDto::from)
                .toList();
    }

    public void addInquiryCount(String productCode) {
        ProductInquiryCount productInquiryCount = productInquiryCountRepository.findByProductCode(productCode)
                .orElse(ProductInquiryCount.createInquiryCountByProductCode(productCode));

        productInquiryCount.updateInquiryCount();

        productInquiryCountRepository.save(productInquiryCount);
    }
}
