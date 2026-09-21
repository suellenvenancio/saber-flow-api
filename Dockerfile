# =========================
# Etapa 1: Build
# =========================
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

# Copia os arquivos do Maven primeiro
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dá permissão para executar o Maven Wrapper
RUN chmod +x mvnw

# Baixa as dependências
RUN ./mvnw dependency:go-offline -DskipTests

# Copia o código
COPY src src

# Compila a aplicação
RUN ./mvnw clean package -DskipTests


# =========================
# Etapa 2: Runtime
# =========================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Porta utilizada pelo Spring Boot
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]