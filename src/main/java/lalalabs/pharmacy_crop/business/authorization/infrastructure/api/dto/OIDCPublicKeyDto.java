package lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record OIDCPublicKeyDto(
        String kid,
        String kty,
        String alg,
        String use,
        String n,
        String e
) {
}
