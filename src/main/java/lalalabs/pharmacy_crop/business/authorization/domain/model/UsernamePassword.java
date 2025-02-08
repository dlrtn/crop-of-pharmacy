package lalalabs.pharmacy_crop.business.authorization.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePassword {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
