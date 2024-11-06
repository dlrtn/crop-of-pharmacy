package lalalabs.pharmacy_crop.business.authorization.infrastructure.dto;

import java.util.List;

public record OIDCPublicKeysResponse(
        List<OIDCPublicKeyDto> keys
) {
    public void print() {
        keys.forEach(OIDCPublicKeyDto::print);
    }
}
