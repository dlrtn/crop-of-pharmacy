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
@Table(name = "product")
// 회계기에 따라 테이블 데이터가 변경됩니다. 인지 후, 지속적으로 마이그레이션 해주어야 합니다.
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "product_code", length = 12)
    private String productCode;

    @Column(name = "confirm")
    private String confirm;

    @Column(name = "purchaser")
    private String purchaser;

    @Column(name = "registered")
    private String registered;

    @Column(name = "remark")
    private String remark;

    @Column(name = "name")
    private String name;

    @Column(name = "volume")
    private String volume;

    @Column(name = "quantity_per_box", length = 4)
    private String quantityPerBox;

    @Column(name = "color")
    private String color;

    @Column(name = "company")
    private String company;

    @Column(name = "mechanism")
    private String mechanism;

    @Column(name = "product_name")
    private String productName;

    // 출고 단가
    @Column(name = "release_price")
    private Integer releasePaymentUnitPrice;

    // 정상 단가
    @Column(name = "normal_price")
    private Integer normalPrice;


    public String getBoxUnitDescription() {
        return "개";
    }

    public String getPack() {
        return quantityPerBox + " " + getBoxUnitDescription();
    }
}
