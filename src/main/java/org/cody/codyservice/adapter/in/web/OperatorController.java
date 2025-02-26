package org.cody.codyservice.adapter.in.web;

import org.cody.codyservice.application.operator.CreateProductUseCase;
import org.cody.codyservice.application.operator.DeleteProductUseCase;
import org.cody.codyservice.application.operator.UpdateProductUseCase;
import org.cody.codyservice.common.ApiResponse;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.Product;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final BrandRepository brandRepository;
    
    @Autowired
    public OperatorController(CreateProductUseCase createProductUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             BrandRepository brandRepository) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.brandRepository = brandRepository;
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
        Brand createdBrand = brandRepository.save(brand);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 생성되었습니다.", createdBrand));
    }
    
    @PutMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<Brand>> updateBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        Brand existingBrand = brandRepository.findById(id);
        if (existingBrand == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "브랜드를 찾을 수 없습니다: " + id, null));
        }
        
        brand.setBrandId(id);
        Brand updatedBrand = brandRepository.save(brand);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 수정되었습니다.", updatedBrand));
    }
    
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer id) {
        brandRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "브랜드가 삭제되었습니다.", null));
    }
} 