package lalalabs.pharmacy_crop.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lalalabs.pharmacy_crop.business.product.domain.ProductPurpose;

@Converter(autoApply = true)
public class ProductPurposeConverter implements AttributeConverter<ProductPurpose, String> {

    @Override
    public String convertToDatabaseColumn(ProductPurpose attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ProductPurpose convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (ProductPurpose category : ProductPurpose.values()) {
            if (category.getCode().equals(dbData)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown code for PesticideType: " + dbData);
    }
}
