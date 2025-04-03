# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
LABEL author=JohnAlmaraz

# Copiar el wrapper de Maven y el pom.xml para aprovechar la cache de dependencias
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copiar el resto del código y compilar el jar
COPY src src
RUN ./mvnw package -DskipTests

# Stage 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]