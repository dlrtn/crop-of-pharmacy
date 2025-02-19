package lalalabs.pharmacy_crop.business.product.api.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ProductCommandRequest {

    @Length(min = 1, max = 100)
    private Integer confirm;

    @Length(min = 1, max = 100)
    private String remark;

    private String itemCode;

    @Length(min = 2, max = 2)
    private String purchaser2;

    private Integer normalPrice;

    @Length(min = 3, max = 3)
    private String registered;

    private String color;

    private Integer discountPrice;

    private String name;

    @Length(min = 2, max = 2)
    private String purchaser;

    private String normalName;

    private String mainIngrediantContent;

    @Length(min = 4, max = 4)
    private String item;

    private String etc;

    @Length(min = 2, max = 2)
    private String purpose2;

    private String workspace;

    private String mechanism;

    private Integer quantityPerBox;

    private String formulation;

    private String volume;

    private Integer itemCount;

    private String productCode;

    private String company;

    private Integer serialNumber;

    private String itemName;

    private String category;

    @Length(min = 2, max = 2)
    private String purpose;

}
