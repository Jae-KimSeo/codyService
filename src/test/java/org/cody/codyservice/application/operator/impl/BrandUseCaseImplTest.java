package org.cody.codyservice.application.operator.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrandUseCaseImplTest {

    @Mock
    private BrandRepository brandRepository;
    
    private GetBrandsUseCaseImpl getBrandsUseCase;
    private CreateBrandUseCaseImpl createBrandUseCase;
    private UpdateBrandUseCaseImpl updateBrandUseCase;
    private DeleteBrandUseCaseImpl deleteBrandUseCase;
    
    private Brand sampleBrand;
    private Brand existingBrand;
    
    @BeforeEach
    void setUp() {
        getBrandsUseCase = new GetBrandsUseCaseImpl(brandRepository);
        createBrandUseCase = new CreateBrandUseCaseImpl(brandRepository);
        updateBrandUseCase = new UpdateBrandUseCaseImpl(brandRepository);
        deleteBrandUseCase = new DeleteBrandUseCaseImpl(brandRepository);
        
        sampleBrand = new Brand(1, "테스트 브랜드", LocalDateTime.now(), LocalDateTime.now());
        
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 0, 0);
        existingBrand = new Brand(1, "기존 브랜드", createdAt, createdAt);
    }
    
    @Nested
    @DisplayName("Get Brands 유스케이스 테스트")
    class GetBrandsTests {
        @Test
        @DisplayName("브랜드 ID로 브랜드를 성공적으로 조회한다")
        void getBrandById_WhenBrandExists_ShouldReturnBrand() {
            when(brandRepository.findById(1)).thenReturn(sampleBrand);
            
            Brand result = getBrandsUseCase.getBrandById(1);
            
            assertNotNull(result);
            assertEquals(1, result.getBrandId());
            assertEquals("테스트 브랜드", result.getName());
            
            verify(brandRepository, times(1)).findById(1);
        }
        
        @Test
        @DisplayName("존재하지 않는 브랜드 ID로 조회 시 BusinessException이 발생한다")
        void getBrandById_WhenBrandDoesNotExist_ShouldThrowBusinessException() {
            when(brandRepository.findById(99)).thenReturn(null);
            
            assertThrows(BusinessException.class, () -> getBrandsUseCase.getBrandById(99));
            verify(brandRepository, times(1)).findById(99);
        }
        
        @Test
        @DisplayName("전체 브랜드 목록을 성공적으로 조회한다")
        void getAllBrands_ShouldReturnAllBrands() {
            List<Brand> brands = Arrays.asList(
                sampleBrand,
                new Brand(2, "두 번째 브랜드", LocalDateTime.now(), LocalDateTime.now())
            );
            when(brandRepository.findAll()).thenReturn(brands);
            
            List<Brand> result = getBrandsUseCase.getAllBrands();
            
            assertNotNull(result);
            assertEquals(2, result.size());
            
            verify(brandRepository, times(1)).findAll();
        }
    }
    
    @Nested
    @DisplayName("Create Brand 유스케이스 테스트")
    class CreateBrandTests {
        @Test
        @DisplayName("유효한 브랜드 정보로 브랜드를 생성한다")
        void createBrand_WithValidData_ShouldCreateBrand() {
            Brand inputBrand = new Brand(null, "새 브랜드");
            Brand savedBrand = new Brand(1, "새 브랜드", LocalDateTime.now(), LocalDateTime.now());
            
            when(brandRepository.save(any(Brand.class))).thenReturn(savedBrand);
            
            Brand result = createBrandUseCase.createBrand(inputBrand);
            
            assertNotNull(result);
            assertEquals(1, result.getBrandId());
            assertEquals("새 브랜드", result.getName());
            
            verify(brandRepository, times(1)).save(any(Brand.class));
        }
        
        @Test
        @DisplayName("브랜드 이름이 null이면 IllegalArgumentException이 발생한다")
        void createBrand_WithNullName_ShouldThrowIllegalArgumentException() {
            Brand invalidBrand = new Brand(null, null);
            
            assertThrows(IllegalArgumentException.class, 
                () -> createBrandUseCase.createBrand(invalidBrand));
            
            verify(brandRepository, never()).save(any(Brand.class));
        }
    }
    
    @Nested
    @DisplayName("Update Brand 유스케이스 테스트")
    class UpdateBrandTests {
        @Test
        @DisplayName("존재하는 브랜드를 성공적으로 업데이트한다")
        void updateBrand_WithExistingBrand_ShouldUpdateBrand() {
            when(brandRepository.findById(1)).thenReturn(existingBrand);
            
            Brand updateData = new Brand(null, "수정된 브랜드");
            Brand updatedBrand = new Brand(1, "수정된 브랜드", existingBrand.getCreatedAt(), LocalDateTime.now());
            
            when(brandRepository.save(any(Brand.class))).thenReturn(updatedBrand);
            
            Brand result = updateBrandUseCase.updateBrand(1, updateData);
            
            assertNotNull(result);
            assertEquals("수정된 브랜드", result.getName());
            
            verify(brandRepository, times(1)).findById(1);
            verify(brandRepository, times(1)).save(any(Brand.class));
        }
        
        @Test
        @DisplayName("존재하지 않는 브랜드 ID로 업데이트를 시도하면 BusinessException이 발생한다")
        void updateBrand_WithNonExistingId_ShouldThrowBusinessException() {
            when(brandRepository.findById(99)).thenReturn(null);
            
            Brand updateData = new Brand(null, "수정된 브랜드");
            
            assertThrows(BusinessException.class, 
                () -> updateBrandUseCase.updateBrand(99, updateData));
            
            verify(brandRepository, times(1)).findById(99);
            verify(brandRepository, never()).save(any(Brand.class));
        }
    }
    
    @Nested
    @DisplayName("Delete Brand 유스케이스 테스트")
    class DeleteBrandTests {
        @Test
        @DisplayName("존재하는 브랜드를 성공적으로 삭제한다")
        void deleteBrand_WithExistingBrand_ShouldDeleteBrand() {
            Brand existingBrand = new Brand(1, "삭제할 브랜드");
            LocalDateTime now = LocalDateTime.now();
            existingBrand.setCreatedAt(now);
            existingBrand.setUpdatedAt(now);
            
            when(brandRepository.findById(1)).thenReturn(existingBrand);
            
            assertDoesNotThrow(() -> deleteBrandUseCase.deleteBrand(1));
            
            verify(brandRepository, times(1)).findById(1);
            verify(brandRepository, times(1)).deleteById(1);
        }
        
        @Test
        @DisplayName("존재하지 않는 브랜드 ID로 삭제를 시도하면 BusinessException이 발생한다")
        void deleteBrand_WithNonExistingId_ShouldThrowBusinessException() {
            when(brandRepository.findById(99)).thenReturn(null);
            
            assertThrows(BusinessException.class, 
                () -> deleteBrandUseCase.deleteBrand(99));
            
            verify(brandRepository, times(1)).findById(99);
            verify(brandRepository, never()).deleteById(any());
        }
    }
} 