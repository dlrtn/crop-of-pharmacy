package lalalabs.pharmacy_crop.business.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_MASTER("마스터"), ROLE_ADMIN("관리자"), ROLE_USER("정회원"), ROLE_MEMBER("일반회원");

    Role(String s) {
        this.name = s;
    }

    private final String name;
}
