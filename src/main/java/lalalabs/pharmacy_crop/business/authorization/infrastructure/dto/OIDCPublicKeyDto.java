package lalalabs.pharmacy_crop.business.authorization.infrastructure.dto;

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

    public void print() {
        log.info("kid: " + kid);
        log.info("kty: " + kty);
        log.info("alg: " + alg);
        log.info("use: " + use);
        log.info("n: " + n);
        log.info("e: " + e);
    }
}
