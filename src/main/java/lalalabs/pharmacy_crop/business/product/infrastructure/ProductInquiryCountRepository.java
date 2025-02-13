package lalalabs.pharmacy_crop.business.product.infrastructure;

import lalalabs.pharmacy_crop.business.product.domain.ProductInquiryCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInquiryCountRepository extends JpaRepository<ProductInquiryCount, Integer> {
    List<ProductInquiryCount> findTop10ByOrderByInquiryCountDesc();

    Optional<ProductInquiryCount> findByProductCode(String productCode);
}
