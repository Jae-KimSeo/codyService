# Cody Service

## 목차
- [개요](#개요)
- [ERD (Entity Relationship Diagram)](#erd-entity-relationship-diagram)
- [아키텍처 구조도](#아키텍처-구조도)
- [서버 실행 가이드라인](#서버-실행-가이드라인)
- [통합 테스트 실행](#통합-테스트-실행)
- [API 명세서](#api-명세서)

## 개요
본 프로젝트는 Cody Service를 제공하는 백엔드 애플리케이션입니다. 현재 오퍼레이터와 코디 서비스가 구현되었으며, 노티 서비스는 초기 기획 단계에서 고려되었으나 구현 범위를 넘어서 현재 구현되지 않았습니다.

프로젝트는 Redis를 활용한 CQRS(Command Query Responsibility Segregation) 패턴을 적용하여 데이터의 읽기와 쓰기 작업을 분리하고 성능을 최적화했습니다.

## ERD (Entity Relationship Diagram)
아래는 프로젝트의 ERD입니다.

<img width="80%" alt="ERD" src="https://github.com/user-attachments/assets/a5bc90b3-e209-40d6-8977-70d9b9d6c076" />

## 아키텍처 구조도
아래는 프로젝트의 아키텍처 구조도입니다.

<img width="100%" alt="아키텍처 구조도" src="https://github.com/user-attachments/assets/d388f794-510b-4db2-ad4b-36c6bb75a424" />

## 서버 실행 가이드라인

### 기본 환경 준비
1. Java 설치 (JDK 11 이상 권장)
2. Gradle 설치 (또는 프로젝트의 gradlew 사용)

### 프로젝트 빌드
프로젝트 루트 디렉토리에서 빌드 실행

빌드
```bash
./gradlew clean build
```

테스트 실행 (빌드 포함)
```bash
## 루트폴더에서
chmode +x ./start-server.sh
./start-server.sh
```

## API 명세서

서비스 기본 URL: http://localhost:8080/api

### 1. Cody API
상품 가격 정보 및 코디 관련 조회 API입니다.

#### 1.1 카테고리별 최저가격 브랜드 및 상품 정보 조회
- URL: http://localhost:8080/api/cody/categories/{categoryId}/lowest-price
- Method: GET
- Path Variable: categoryId (카테고리 ID)
- 설명: 특정 카테고리에서 가장 낮은 가격의 상품 정보를 조회합니다.
- 예시 요청: http://localhost:8080/api/cody/categories/1/lowest-price

#### 1.2 단일 브랜드의 모든 카테고리 최저가격 정보 조회
- URL: http://localhost:8080/api/cody/brands/{brandId}/categories
- Method: GET
- Path Variable: brandId (브랜드 ID)
- 설명: 특정 브랜드의 모든 카테고리별 최저가격 상품 정보를 조회합니다.
- 예시 요청: http://localhost:8080/api/cody/brands/1/categories

#### 1.3 카테고리별 가격 범위 및 통계 정보 조회
- URL: http://localhost:8080/api/cody/categories/{categoryId}/price-range
- Method: GET
- Path Variable: categoryId (카테고리 ID)
- 설명: 특정 카테고리의 최저가 및 최고가 상품 정보를 조회합니다.
- 예시 요청: http://localhost:8080/api/cody/categories/3/price-range

### 2. Operator API
브랜드와 상품 관리를 위한 API입니다.

#### 2.1 브랜드 생성
- URL: http://localhost:8080/api/operator/brands
- Method: POST
- Content-Type: application/json
- 설명: 새 브랜드를 생성합니다.
- 예시 요청: http://localhost:8080/api/operator/brands
Body:
```
{
  "brandId": 5,
  "name": "스톤아일랜드"
}
```

예시 응답:
```
{
  "success": true,
  "message": "브랜드가 성공적으로 생성되었습니다.",
  "data": {
    "brandId": 5,
    "name": "스톤아일랜드"
  }
}
```
#### 2.2 브랜드 정보 업데이트
- URL: http://localhost:8080/api/operator/brands/{brandId}
- Method: PUT
- Path Variable: brandId (브랜드 ID)
- Content-Type: application/json
- 설명: 기존 브랜드 정보를 업데이트합니다.
- 예시 요청: http://localhost:8080/api/operator/brands/5
Body: