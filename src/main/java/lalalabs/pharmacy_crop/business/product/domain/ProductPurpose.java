package lalalabs.pharmacy_crop.business.product.domain;

import lombok.Getter;

@Getter
public enum ProductPurpose {
    전착("AD"),

    살균_세균("FB"),
    살균_바이로드("FD"),
    살균_진균("FF"),
    균충("FI"),
    살균_마이코플라스마("FM"),
    살균_바이러스("FV"),

    제초_원예("HF"),
    초충("HI"),
    제초_수도("HP"),
    제초_경엽("HS"),
    제초_잔디("HT"),
    제초_이행형전멸("HX"),
    제초_접촉형전멸("Hy"),

    살충_응애("IA"),
    살충_선충("IN"),
    살충_포괄("IP"),

    유기농업_살균제("OF"),
    유기농업_살충제("OI"),
    유기농업_제초제("OH"),

    생조("PG"),
    종자("SE"),
    농자재("XA"),
    편의용품("XC"),
    농기계("XE"),
    비료("XF"),
    방역제품("XP"),
    보호장구("XS"),
    배양토_상토("XV"),
    기타("XX"),
    미등록("##");

    public final String code;

    ProductPurpose(String code) {
        this.code = code;
    }

    public static ProductPurpose fromCode(String code) {
        for (ProductPurpose category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
