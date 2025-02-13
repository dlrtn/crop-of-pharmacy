package lalalabs.pharmacy_crop.business.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductSummaryDto;
import lalalabs.pharmacy_crop.business.product.application.ProductService;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상품", description = "상품을 조회합니다.")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiHeader
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts(
            @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page,
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size) {
        List<ProductSummaryDto> productSummaryDtoList = productService.read(page, size);

        return ResponseEntity.ok(SuccessResponse.of(productSummaryDtoList));
    }

    @ApiHeader
    @Operation(summary = "상품 상세 조회", description = "상품 상세를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductDetail(@Parameter(name = "id", description = "상품 ID", required = true) @RequestParam String id) {
        ProductDetailDto productDetailDto = productService.readById(id);

        return ResponseEntity.ok(SuccessResponse.of(productDetailDto));
    }

    @ApiHeader
    @Operation(summary = "상품 검색", description = "상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProduct(
            @Parameter(name = "keyword", description = "검색어", required = true) @RequestParam String keyword,
            @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page,
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size) {
        List<ProductSummaryDto> productSummaryDtoList = productService.search(keyword, page, size);

        return ResponseEntity.ok(SuccessResponse.of(productSummaryDtoList));
    }
}
