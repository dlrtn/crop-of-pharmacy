package lalalabs.pharmacy_crop.business.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductCommandRequest;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductDetailDto;
import lalalabs.pharmacy_crop.business.product.api.dto.ProductSummaryDto;
import lalalabs.pharmacy_crop.business.product.application.ProductService;
import lalalabs.pharmacy_crop.business.product.domain.ProductCategory;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.security.OnlyAdmin;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        productService.delete(productId);

        return ResponseEntity.ok(SuccessResponse.of("상품이 삭제되었습니다."));
    }

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @PatchMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(
            @Parameter(name = "file", description = "파일") @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable String productId,
            @Parameter(name = "product", description = "상품 정보, json 형태(예시 : {   \"confirm\": 1,   \"remark\": \"상품 비고 내용\",   \"itemCode\": \"ITEM123\",   \"purchaser2\": \"AB\",   \"normalPrice\": 10000,   \"registered\": \"YES\",   \"color\": \"Red\",   \"discountPrice\": 8000,   \"name\": \"상품명\",   \"purchaser\": \"CD\",   \"normalName\": \"일반명\",   \"mainIngrediantContent\": \"주성분 내용\",   \"item\": \"ITEM\",   \"etc\": \"기타 정보\",   \"purpose2\": \"EF\",   \"workspace\": \"작업공간\",   \"mechanism\": \"작용 기전\",   \"quantityPerBox\": 10,   \"formulation\": \"제형\",   \"volume\": \"500ml\",   \"itemCount\": 5,   \"productCode\": \"PROD-001\",   \"company\": \"제조사\",   \"serialNumber\": 123456,   \"itemName\": \"상품명\",   \"category\": \"농약\", \"purpose\": \"GH\" })") @RequestParam(value = "product") String productJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCommandRequest productCommandRequest = objectMapper.readValue(productJson, ProductCommandRequest.class);

        productService.update(productId, productCommandRequest, file);

        return ResponseEntity.ok(SuccessResponse.of("상품이 수정되었습니다."));
    }

    @ApiHeader
    @OnlyAdmin
    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createProduct(
            @Parameter(name = "file", description = "파일") @RequestPart(value = "file", required = false) MultipartFile file,
            @Parameter(name = "product", description = "상품 정보, json 형태(예시 : {   \"confirm\": 1,   \"remark\": \"상품 비고 내용\",   \"itemCode\": \"ITEM123\",   \"purchaser2\": \"AB\",   \"normalPrice\": 10000,   \"registered\": \"YES\",   \"color\": \"Red\",   \"discountPrice\": 8000,   \"name\": \"상품명\",   \"purchaser\": \"CD\",   \"normalName\": \"일반명\",   \"mainIngrediantContent\": \"주성분 내용\",   \"item\": \"ITEM\",   \"etc\": \"기타 정보\",   \"purpose2\": \"EF\",   \"workspace\": \"작업공간\",   \"mechanism\": \"작용 기전\",   \"quantityPerBox\": 10,   \"formulation\": \"제형\",   \"volume\": \"500ml\",   \"itemCount\": 5,   \"productCode\": \"PROD-001\",   \"company\": \"제조사\",   \"serialNumber\": 123456,   \"itemName\": \"상품명\",   \"category\": \"농약\", \"purpose\": \"GH\" })") @RequestParam(value = "product") String productJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCommandRequest productCommandRequest = objectMapper.readValue(productJson, ProductCommandRequest.class);

        productService.create(productCommandRequest, file);

        return ResponseEntity.ok(SuccessResponse.of("상품이 등록되었습니다."));
    }
}
