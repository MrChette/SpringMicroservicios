#La etapa 1 se destruye automáticamente después de la construcción, dejando solo la imagen final con el JAR empaquetado.
#La etapa 2 es la que se ejecutará en producción, usando solo el JRE para ejecutar la aplicación, lo que reduce el tamaño de la imagen final y mejora la seguridad.

# Etapa 1: Construcción de la aplicación
# Usamos jdk 21 con Maven para construir la aplicación
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
# Copiamos los archivos del proyecto al contenedor
COPY . .
# Ejecutamos Maven para compilar y empaquetar la aplicación, omitiendo las pruebas
RUN mvn clean package -DskipTests

# Etapa 2: Creación de la imagen final
# Usamos jre 21 para ejecutar la aplicación
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiamos el archivo JAR generado en la etapa de construcción
COPY --from=builder /app/target/*.jar product-service.jar
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "product-service.jar"]

