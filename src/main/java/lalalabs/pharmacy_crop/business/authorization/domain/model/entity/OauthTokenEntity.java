package lalalabs.pharmacy_crop.business.authorization.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OauthTokenEntity extends BaseTimeEntity {
    @Id
    private String id;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Column
    private String expriesIn;
}
