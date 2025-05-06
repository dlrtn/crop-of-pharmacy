package lalalabs.pharmacy_crop.business.product.infrastructure;

import lalalabs.pharmacy_crop.business.product.domain.Product;
import lalalabs.pharmacy_crop.business.product.domain.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByNameContaining(String keyword, Pageable pageable);

    List<Product> findByCategoryNotInAndNormalPriceIsNotNull(List<ProductCategory> categoryList, Pageable pageable);

    List<Product> findAllByNormalPriceIsNotNull(Pageable pageable);

    Optional<Product> findByProductCode(String productCode);

    List<Product> findByCategoryAndNormalPriceIsNotNull(ProductCategory category, Pageable pageable);

    List<Product> findAllByProductCodeIn(Collection<String> productCodes);
}
