package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.common.ProductPurposeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
// 회계기에 따라 테이블 데이터가 변경됩니다. 인지 후, 지속적으로 마이그레이션 해주어야 합니다.
public class Product {

    @Id
    @Column(name = "product_code", length = 12, nullable = false)
    private String productCode;

    @Column(name = "name")
    private String name;

    // 출고 단가
    @Column(name = "release_invoice_unit_price")
    private Integer releasePaymentUnitPrice;

    // 정상 단가
    @Column(name = "normal_price")
    private Integer normalPrice;

    @Column(name = "volume")
    private String volume;

    @Column(name = "quantity_per_box", length = 4)
    private String quantityPerBox;

    @Column(name = "box_unit")
    private String boxUnit;

    @Column(name = "purpose")
    @Convert(converter = ProductPurposeConverter.class)
    private ProductPurpose purpose;

    public String getBoxUnitDescription() {
        switch (boxUnit) {
            case "1":
                return "병";
            case "2":
                return "봉";
            case "3":
                return "EA";
            case "4":
                return "포";
            case "5":
                return "통";
            case "6":
                return "Ex";
            case "7":
                return "롤";
            default:
                return "Unknown";
        }
    }

    public String getPack() {
        return quantityPerBox + " " + getBoxUnitDescription();
    }
}
