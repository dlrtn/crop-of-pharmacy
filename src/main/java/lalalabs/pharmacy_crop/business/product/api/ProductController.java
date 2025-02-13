package lalalabs.pharmacy_crop.business.product.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductSummaryDto;
import lalalabs.pharmacy_crop.business.product.application.ProductService;
import lalalabs.pharmacy_crop.business.product.domain.ProductCategory;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiHeader
    @Operation(summary = "상품 카테고리별 조회", description = "상품을 카테고리별로 조회합니다.")
    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategory(
            @Parameter(name = "category", description = "카테고리", required = true) @RequestParam ProductCategory category,
            @Parameter(name = "page", description = "페이지 번호(0 ~ )", required = true) @RequestParam int page,
            @Parameter(name = "size", description = "페이지 크기", required = true) @RequestParam int size) {
        List<ProductSummaryDto> productSummaryDtoList = productService.readByCategory(category, page, size);

        return ResponseEntity.ok(SuccessResponse.of(productSummaryDtoList));
    }

    @ApiHeader
    @Operation(summary = "월간 인기 상품 조회", description = "월간 인기 상품을 조회합니다.")
    @GetMapping("/monthly-best")
    public ResponseEntity<ApiResponse> getMonthlyBestProducts() {
        List<ProductSummaryDto> productSummaryDtoList = productService.readMonthlyBest();

        return ResponseEntity.ok(SuccessResponse.of(productSummaryDtoList));
    }

    @ApiHeader
    @Operation(summary = "상품 문의하기 횟수 추가", description = "상품 문의하기 횟수를 추가합니다.")
    @PostMapping("/inquiry-count")
    public ResponseEntity<ApiResponse> addInquiryCount(
            @Parameter(name = "productCode", description = "상품 코드", required = true) @RequestParam String productCode) {
        productService.addInquiryCount(productCode);

        return ResponseEntity.ok(SuccessResponse.of("상품 문의하기 횟수가 추가되었습니다."));
    }
}
