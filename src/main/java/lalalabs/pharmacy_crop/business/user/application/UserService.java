package lalalabs.pharmacy_crop.business.user.application;

import lalalabs.pharmacy_crop.business.user.api.dto.UserDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserQueryService userQueryService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        OauthUser user = userQueryService.findByUserId(userId);

        return new OauthUserDetails(user);
    }

    public UserDto getUser(String userId) {
        OauthUser user = userQueryService.findByUserId(userId);

        return UserDto.from(user);
    }

    public List<OauthUser> getUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return userQueryService.findAll(pageable);
    }
}
