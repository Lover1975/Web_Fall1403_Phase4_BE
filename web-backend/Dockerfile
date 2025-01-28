FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/web-backend

COPY web-backend/mvnw .
COPY web-backend/mvnw.cmd .
COPY web-backend/.mvn .mvn

COPY web-backend/pom.xml .
COPY web-backend/src src

RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/web-backend/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.webbackend.WebBackendApplication"]

