package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmGuardAnswerDto {
    private String content;
    private int productId;

    public FarmGuardAnswerDto(String content, int productId) {
        this.content = content;
        this.productId = productId;
    }

    public static FarmGuardAnswerDto fromDomain(FarmGuardAnswer answer) {
        return new FarmGuardAnswerDto(answer.getContent(), answer.getProductId());
    }
}
