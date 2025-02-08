package lalalabs.pharmacy_crop.business.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.business.user.api.dto.LoginAdminRequest;
import lalalabs.pharmacy_crop.business.user.api.dto.RegisterAdminRequest;
import lalalabs.pharmacy_crop.business.user.application.AdminService;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "관리자", description = "관리자의 회원 가입, 정보 변경, 관리를 수행합니다.")
@RequiredArgsConstructor
@RequestMapping("/admin-users")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 회원 가입", description = "관리자 회원을 가입합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@RequestBody RegisterAdminRequest request) {
        adminService.createUser(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "관리자 로그인", description = "관리자 회원을 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginAdminRequest request) {
        JwtTokensDto jwtTokensDto = adminService.login(request);

        return ResponseEntity.ok().body(SuccessResponse.of(jwtTokensDto));
    }
}
