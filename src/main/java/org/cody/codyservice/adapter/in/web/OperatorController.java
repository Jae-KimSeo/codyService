package org.cody.codyservice.adapter.in.web;

import java.util.List;

import org.cody.codyservice.application.operator.CreateBrandUseCase;
import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.application.operator.DeleteBrandUseCase;
import org.cody.codyservice.application.operator.DeleteProductUseCase;
import org.cody.codyservice.application.operator.GetBrandsUseCase;
import org.cody.codyservice.application.operator.GetProductsUseCase;
import org.cody.codyservice.application.operator.UpdateBrandUseCase;
import org.cody.codyservice.application.operator.UpdateProductUseCase;
import org.cody.codyservice.common.ApiResponse;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetProductsUseCase getProductsUseCase;
    private final CreateBrandUseCase createBrandUseCase;
    private final UpdateBrandUseCase updateBrandUseCase;
    private final DeleteBrandUseCase deleteBrandUseCase;
    private final GetBrandsUseCase getBrandsUseCase;
    
    @Autowired
    public OperatorController(CreateProductUseCase createProductUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             GetProductsUseCase getProductsUseCase,
                             CreateBrandUseCase createBrandUseCase,
                             UpdateBrandUseCase updateBrandUseCase,
                             DeleteBrandUseCase deleteBrandUseCase,
                             GetBrandsUseCase getBrandsUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.getProductsUseCase = getProductsUseCase;
        this.createBrandUseCase = createBrandUseCase;
        this.updateBrandUseCase = updateBrandUseCase;
        this.deleteBrandUseCase = deleteBrandUseCase;
        this.getBrandsUseCase = getBrandsUseCase;
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable Integer id) {
        Product product = getProductsUseCase.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "상품 조회 성공", product));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = getProductsUseCase.getAllProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, "전체 상품 목록 조회 성공", products));
    }
    
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = createProductUseCase.createProduct(product);
        return ResponseEntity.ok(new ApiResponse<>(true, "상품이 생성되었습니다.", createdProduct));
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product updatedProduct = updateProductUseCase.updateProduct(id, product);
        return ResponseEntity.ok(new ApiResponse<>(true, "상품이 수정되었습니다.", updatedProduct));
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "상품이 삭제되었습니다.", null));
    }

    @PostMapping("/brands")
    public ResponseEntity<ApiResponse<Brand>> createBrand(@RequestBody Brand brand) {
        Brand createdBrand = createBrandUseCase.createBrand(brand);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 생성되었습니다.", createdBrand));
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<Brand>> updateBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        Brand updatedBrand = updateBrandUseCase.updateBrand(id, brand);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 수정되었습니다.", updatedBrand));
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer id) {
        deleteBrandUseCase.deleteBrand(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 삭제되었습니다.", null));
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<Brand>> getBrand(@PathVariable Integer id) {
        Brand brand = getBrandsUseCase.getBrandById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드 조회 성공", brand));
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<Brand>>> getAllBrands() {
        List<Brand> brands = getBrandsUseCase.getBrands();
        return ResponseEntity.ok(new ApiResponse<>(true, "전체 브랜드 목록 조회 성공", brands));
    }
} 