package lalalabs.pharmacy_crop.business.product.infrastructure;

import lalalabs.pharmacy_crop.business.product.domain.Product;
import lalalabs.pharmacy_crop.business.product.domain.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByNameContaining(String keyword, Pageable pageable);

    List<Product> findByCategoryNotIn(List<ProductCategory> categoryList, Pageable pageable);

    List<Product> findByCategory(ProductCategory category, Pageable pageable);

    List<Product> findAllByNormalPriceIsNotNull(Pageable pageable);
}
