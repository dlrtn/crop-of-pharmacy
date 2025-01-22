package lalalabs.pharmacy_crop.business.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TbPum039")
// 회계기에 따라 테이블 데이터가 변경됩니다. 인지 후, 지속적으로 마이그레이션 해주어야 합니다.
public class Product {

    @Id
    @Column(name = "fPcode", length = 12, nullable = false)
    private String productId;

    @Column(name = "FSCODE", length = 3)
    private String fscode;

    @Column(name = "FCODE", length = 3)
    private String fcode;

    @Column(name = "FNAME")
    private String fname;

    // 출고 단가
    @Column(name = "FCDANGA")
    private Integer fcdanga;

    // 정상 단가
    @Column(name = "FTDANGA")
    private Integer ftdanga;

    @Column(name = "FUNIT", length = 4)
    private String funit;

    @Column(name = "FUNITGUB")
    private String funitgub;

    @Column(name = "FBOX", length = 4)
    private String fbox;

    @Column(name = "FBOXGUB")
    private String fboxgub;

    public String getFunitGubDescription() {
        switch (funitgub) {
            case "1": return "㎖";  // ml
            case "2": return "ℓ";   // L
            case "3": return "g";
            case "4": return "㎏";  // kg
            case "5": return "Ex";
            case "6": return "m";
            case "7": return "t";
            case "8": return "mm";
            default:
                return "Unknown";
        }
    }

    public String getFboxGubDescription() {
        switch (fboxgub) {
            case "1": return "병";
            case "2": return "봉";
            case "3": return "EA";
            case "4": return "포";
            case "5": return "통";
            case "6": return "Ex";
            case "7": return "롤";
            default:
                return "Unknown";
        }
    }

    public String getModelName() {
        if (fscode == null || fcode == null) {
            return "Unknown";
        }
        return fscode + fcode;
    }

    public String getUnit() {
        return funit + getFunitGubDescription();
    }

    public String getPack() {
        return fbox + getFboxGubDescription();
    }
}
