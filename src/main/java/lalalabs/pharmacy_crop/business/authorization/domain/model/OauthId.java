package lalalabs.pharmacy_crop.business.authorization.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthId {

    @Column(name = "oauth_id")
    private String oauthServerId;

    @Column(name = "oauth_service_type")
    @Enumerated(EnumType.STRING)
    private OauthServiceType oauthServiceType;

}