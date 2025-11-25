# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
# Imagen base ligera de Alpine Linux (~5MB) para compilar el código
# Se usa "AS build" para nombrar esta etapa y referenciarla después
FROM alpine:latest AS build

# Actualizar el índice de paquetes de Alpine
RUN apk update

# Instalar OpenJDK 17 y dos2unix (para convertir fin de línea)
# Alpine usa 'apk' como gestor de paquetes (equivalente a apt/yum)
RUN apk add openjdk17 dos2unix

# Copiar archivos de Gradle primero (para aprovechar caché de Docker)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Convertir gradlew a formato Unix y dar permisos de ejecución
# Necesario porque Windows usa CRLF y Linux usa LF
RUN dos2unix ./gradlew || sed -i 's/\r$//' ./gradlew
RUN chmod +x ./gradlew

# Copiar el código fuente
COPY src src

# Ejecutar Gradle para compilar y generar el JAR ejecutable
# bootJar: tarea de Spring Boot que genera un "fat JAR" con todas las dependencias
# --no-daemon: no usar proceso Gradle en segundo plano (mejor para Docker)
# Resultado: build/libs/Mutantes-1.0-SNAPSHOT.jar
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# Imagen base con SOLO el runtime de Java (sin herramientas de compilación)
# Esto reduce el tamaño de la imagen final de ~500MB a ~200MB
# Usamos Eclipse Temurin (reemplazo oficial de OpenJDK)
FROM eclipse-temurin:17-jre-alpine

# Documentar que la aplicación escucha en el puerto 8080
# IMPORTANTE: esto NO abre el puerto, solo es documentación
# El puerto se mapea con: docker run -p 8080:8080
EXPOSE 8080

# Copiar el JAR generado en la ETAPA 1 (build) a la imagen final
# --from=build: tomar archivo de la etapa "build" anterior
# Solo se copia el JAR, NO el código fuente ni herramientas de compilación
# Esto mantiene la imagen final pequeña y segura
COPY --from=build ./build/libs/Mutantes-1.0-SNAPSHOT.jar ./app.jar

# Comando que se ejecuta cuando el contenedor inicia
# ENTRYPOINT (no CMD) asegura que siempre se ejecute la aplicación
# ["java", "-jar", "app.jar"]: formato exec (preferido sobre shell)
ENTRYPOINT ["java", "-jar", "app.jar"]