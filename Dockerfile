# Etapa de construcción
FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /app

# Instalar dos2unix para manejar saltos de línea de Windows
RUN apk add --no-cache dos2unix

# Copiar archivos necesarios para Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Convertir gradlew a formato Unix y darle permisos de ejecución
RUN dos2unix gradlew && chmod +x gradlew

# Descargar dependencias
RUN ./gradlew dependencies --no-daemon

# Copiar el código fuente
COPY src src

# Construir el proyecto
RUN ./gradlew bootJar --no-daemon

# Etapa de ejecución
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción explícitamente excluyendo el "-plain.jar"
COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar

# Exponer el puerto de la aplicación (por defecto 8080)
EXPOSE 8080

# Variable de entorno para configurar perfiles de Spring (opcional)
# ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
