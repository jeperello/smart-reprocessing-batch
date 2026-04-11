# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiamos el maven wrapper y el pom primero para cachear dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiamos el código y compilamos saltando los tests para acelerar el deploy
# (Los tests ya pasaron en el CI de GitHub)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Creamos un usuario no-root por seguridad (Estándar Senior)
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copiamos solo el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Optimización de memoria para contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
