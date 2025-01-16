package lalalabs.pharmacy_crop.business.authorization.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lalalabs.pharmacy_crop.common.time.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "oauth_user")
public class OauthTokenEntity extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "access_token")
    private String accessToken;
}
