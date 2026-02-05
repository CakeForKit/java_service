FROM eclipse-temurin:25-jdk-alpine as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean package -DskipTests
ARG TIMESTAMP
RUN echo "Build timestamp: ${TIMESTAMP}" && \
    ls -la /opt/app/target
#RUN ls -la /opt/app/target/*.jar

FROM eclipse-temurin:25-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ARG TIMESTAMP
RUN echo "Build timestamp: ${TIMESTAMP}" && \
    ls -la /opt/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
