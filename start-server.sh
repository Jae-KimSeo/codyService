#!/bin/bash

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1"
}

cd "$(dirname "${BASH_SOURCE[0]}")"

# 6379 포트 확인
if lsof -Pi :6379 -sTCP:LISTEN -t >/dev/null ; then
    log "6379 포트가 이미 사용 중입니다. Redis 실행을 건너뜁니다..."
else
    log "Redis 서버를 시작합니다..."
    docker-compose up -d redis

    if [ "$(docker-compose ps -q redis)" ]; then
        log "Redis 서버가 실행 중입니다!"
    else
        log "Redis 서버 실행 실패!"
        exit 1
    fi
fi

JAR_FILE=$(find ./build/libs -name "*.jar" -not -name "*-plain.jar" -type f | sort -V | tail -n 1)

if [ -z "$JAR_FILE" ]; then
    log "실행 가능한 JAR 파일을 찾을 수 없습니다. 빌드를 실행합니다..."
    ./gradlew build -x test
    JAR_FILE=$(find ./build/libs -name "*.jar" -not -name "*-plain.jar" -type f | sort -V | tail -n 1)
    
    if [ -z "$JAR_FILE" ]; then
        log "빌드 후에도 실행 가능한 JAR 파일을 찾을 수 없습니다. 실행을 중단합니다."
        exit 1
    fi
fi

log "메인 서버를 시작합니다... ($JAR_FILE)"
nohup java -jar "$JAR_FILE" > server.log 2>&1 &
PID=$!

sleep 5
if ps -p $PID > /dev/null; then
    log "메인 서버 실행 성공! (PID: $PID)"
else
    log "메인 서버 실행 실패!"
    exit 1
fi

log "모든 서비스가 성공적으로 실행되었습니다." 