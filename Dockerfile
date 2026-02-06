FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/cart-0.0.1-SNAPSHOT.jar"]