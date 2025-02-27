# Cody Service

## 목차
- [개요](#개요)
- [ERD (Entity Relationship Diagram)](#erd-entity-relationship-diagram)
- [아키텍처 구조도](#아키텍처-구조도)
- [서버 실행 가이드라인](#서버-실행-가이드라인)
- [통합 테스트 실행](#통합-테스트-실행)

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
3. Redis 설치 (로컬 또는 Docker)

### 프로젝트 빌드
프로젝트 루트 디렉토리에서 빌드 실행

빌드
```bash
./gradlew clean build
```

시작
```bash
Docker로 Redis 실행
docker-compose up -d redis
```

개발 환경으로 실행
```bash
./gradlew bootRun
```