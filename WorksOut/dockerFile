# JDK 17 이미지 기반
FROM eclipse-temurin:17-jdk

# 앱 실행 경로
WORKDIR /app

# build/libs 안에 있는 jar 복사
COPY module-application/module-api/build/libs/*.jar app.jar

# 애플리케이션 외부 포트
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]