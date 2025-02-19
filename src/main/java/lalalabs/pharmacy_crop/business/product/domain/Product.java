package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductCommandRequest;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "item_code")
    private String itemCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "product_code", length = 12)
    private String productCode;

    @Column(name = "confirm")
    private Integer confirm;

    @Column(name = "purpose", length = 2)
    private String purpose;

    @Column(name = "item", length = 4)
    private String item;

    @Column(name = "item_count")
    private Integer itemCount;

    @Column(name = "-")
    private String hyphen;

    @Column(name = "purchaser", length = 2)
    private String purchaser;

    @Column(name = "serial_number")
    private Integer serialNumber;

    @Column(name = "registered")
    private String registered;

    @Column(name = "remark")
    private String remark;

    @Column(name = "purchaser2")
    private String purchaser2;

    @Column(name = "name")
    private String name;

    @Column(name = "volume")
    private String volume;

    @Column(name = "quantity_per_box", length = 4)
    private Integer quantityPerBox;

    @Column(name = "color")
    private String color;

    @Column(name = "company")
    private String company;

    @Column(name = "purpose2")
    private String purpose2;

    @Column(name = "mechanism")
    private String mechanism;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "formulation")
    private String formulation;

    @Column(name = "normal_name")
    private String normalName;

    @Column(name = "main_ingrediant_content")
    private String mainIngrediantContent;

    @Column(name = "workspace")
    private String workspace;

    @Column(name = "etc")
    private String etc;

    @Column(name = "picture")
    private String picture;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "normal_price")
    private Integer normalPrice;

    public static Product createProduct(ProductCommandRequest productCommandRequest, Optional<String> picturePath) {
        return Product.builder()
                .itemCode(productCommandRequest.getItemCode())
                .category(productCommandRequest.getCategory() != null ? ProductCategory.valueOf(productCommandRequest.getCategory()) : ProductCategory.없음)
                .productCode(productCommandRequest.getProductCode())
                .confirm(productCommandRequest.getConfirm())
                .purpose(productCommandRequest.getPurpose())
                .item(productCommandRequest.getItem())
                .itemCount(productCommandRequest.getItemCount())
                .hyphen("-")
                .purchaser(productCommandRequest.getPurchaser())
                .serialNumber(productCommandRequest.getSerialNumber())
                .registered(productCommandRequest.getRegistered())
                .remark(productCommandRequest.getRemark())
                .purchaser2(productCommandRequest.getPurchaser2())
                .name(productCommandRequest.getName())
                .volume(productCommandRequest.getVolume())
                .quantityPerBox(productCommandRequest.getQuantityPerBox())
                .color(productCommandRequest.getColor())
                .company(productCommandRequest.getCompany())
                .purpose2(productCommandRequest.getPurpose2())
                .mechanism(productCommandRequest.getMechanism())
                .itemName(productCommandRequest.getItemName())
                .formulation(productCommandRequest.getFormulation())
                .normalName(productCommandRequest.getNormalName())
                .mainIngrediantContent(productCommandRequest.getMainIngrediantContent())
                .workspace(productCommandRequest.getWorkspace())
                .etc(productCommandRequest.getEtc())
                .picture(picturePath.orElse(null))
                .discountPrice(productCommandRequest.getDiscountPrice())
                .normalPrice(productCommandRequest.getNormalPrice())
                .build();
    }

    public String getBoxUnitDescription() {
        return "개";
    }

    public String getPack() {
        return quantityPerBox + " " + getBoxUnitDescription();
    }

    public void update(ProductCommandRequest productCommandRequest, String picturePath) {
        this.confirm = productCommandRequest.getConfirm() != null ? productCommandRequest.getConfirm() : this.confirm;
        this.remark = productCommandRequest.getRemark() != null ? productCommandRequest.getRemark() : this.remark;
        this.itemCode = productCommandRequest.getItemCode() != null ? productCommandRequest.getItemCode() : this.itemCode;
        this.purchaser2 = productCommandRequest.getPurchaser2() != null ? productCommandRequest.getPurchaser2() : this.purchaser2;
        this.normalPrice = productCommandRequest.getNormalPrice() != null ? productCommandRequest.getNormalPrice() : this.normalPrice;
        this.registered = productCommandRequest.getRegistered() != null ? productCommandRequest.getRegistered() : this.registered;
        this.color = productCommandRequest.getColor() != null ? productCommandRequest.getColor() : this.color;
        this.discountPrice = productCommandRequest.getDiscountPrice() != null ? productCommandRequest.getDiscountPrice() : this.discountPrice;
        this.name = productCommandRequest.getName() != null ? productCommandRequest.getName() : this.name;
        this.purchaser = productCommandRequest.getPurchaser() != null ? productCommandRequest.getPurchaser() : this.purchaser;
        this.normalName = productCommandRequest.getNormalName() != null ? productCommandRequest.getNormalName() : this.normalName;
        this.mainIngrediantContent = productCommandRequest.getMainIngrediantContent() != null ? productCommandRequest.getMainIngrediantContent() : this.mainIngrediantContent;
        this.item = productCommandRequest.getItem() != null ? productCommandRequest.getItem() : this.item;
        this.etc = productCommandRequest.getEtc() != null ? productCommandRequest.getEtc() : this.etc;
        this.purpose2 = productCommandRequest.getPurpose2() != null ? productCommandRequest.getPurpose2() : this.purpose2;
        this.workspace = productCommandRequest.getWorkspace() != null ? productCommandRequest.getWorkspace() : this.workspace;
        this.mechanism = productCommandRequest.getMechanism() != null ? productCommandRequest.getMechanism() : this.mechanism;
        this.quantityPerBox = productCommandRequest.getQuantityPerBox() != null ? productCommandRequest.getQuantityPerBox() : this.quantityPerBox;
        this.formulation = productCommandRequest.getFormulation() != null ? productCommandRequest.getFormulation() : this.formulation;
        this.volume = productCommandRequest.getVolume() != null ? productCommandRequest.getVolume() : this.volume;
        this.itemCount = productCommandRequest.getItemCount() != null ? productCommandRequest.getItemCount() : this.itemCount;
        this.productCode = productCommandRequest.getProductCode() != null ? productCommandRequest.getProductCode() : this.productCode;
        this.company = productCommandRequest.getCompany() != null ? productCommandRequest.getCompany() : this.company;
        this.serialNumber = productCommandRequest.getSerialNumber() != null ? productCommandRequest.getSerialNumber() : this.serialNumber;
        this.itemName = productCommandRequest.getItemName() != null ? productCommandRequest.getItemName() : this.itemName;
        this.category = productCommandRequest.getCategory() != null ? ProductCategory.valueOf(productCommandRequest.getCategory()) : this.category;
        this.picture = picturePath != null ? picturePath : this.picture;
        this.purpose = productCommandRequest.getPurpose() != null ? productCommandRequest.getPurpose() : this.purpose;
    }
}
