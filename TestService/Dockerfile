FROM eclipse-temurin:25-jdk-alpine as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
