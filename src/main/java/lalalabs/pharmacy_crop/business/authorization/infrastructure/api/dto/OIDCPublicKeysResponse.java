package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import java.util.List;

public record OIDCPublicKeysResponse(
        List<OIDCPublicKeyDto> keys
) {
}
