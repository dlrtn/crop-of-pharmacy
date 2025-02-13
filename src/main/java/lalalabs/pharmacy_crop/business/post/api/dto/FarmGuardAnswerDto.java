package lalalabs.pharmacy_crop.business.post.api.dto;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmGuardAnswerDto {
    private String content;
    private String productId;

    public FarmGuardAnswerDto(String content, String productId) {
        this.content = content;
        this.productId = productId;
    }

    public static FarmGuardAnswerDto fromDomain(FarmGuardAnswer answer) {
        return new FarmGuardAnswerDto(answer.getContent(), answer.getProductId());
    }
}
