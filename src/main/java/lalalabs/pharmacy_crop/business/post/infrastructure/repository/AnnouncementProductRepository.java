package lalalabs.pharmacy_crop.business.post.infrastructure.repository;

import lalalabs.pharmacy_crop.business.post.domain.AnnouncementProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementProductRepository extends JpaRepository<AnnouncementProduct, Integer> {
}
