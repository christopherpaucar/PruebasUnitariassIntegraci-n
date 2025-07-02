# Dockerfile para aplicación Java Spring Boot
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Exponer puerto (ajustar según tu aplicación)
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/*.jar"]