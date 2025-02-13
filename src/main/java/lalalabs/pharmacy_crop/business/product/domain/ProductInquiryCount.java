package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_inquiry_count")
public class ProductInquiryCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_code", length = 12)
    private String productCode;

    @Column(name = "inquiry_count")
    private Integer inquiryCount;

    public void updateInquiryCount() {
        this.inquiryCount++;
    }

    public static ProductInquiryCount createInquiryCountByProductCode(String productCode) {
        return ProductInquiryCount.builder()
                .productCode(productCode)
                .inquiryCount(0)
                .build();
    }
}
