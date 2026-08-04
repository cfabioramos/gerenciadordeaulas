# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy built application from builder
COPY --from=builder /app/target/gerenciadoraulas-*.jar /app/gerenciadoraulas.jar
COPY --from=builder /app/target/gerenciadoraulas-*.jar.original /app/gerenciadoraulas-original.jar 2>/dev/null || true

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+ParallelRefProcEnabled"
ENV APP_PORT=8080

# Expose port
EXPOSE ${APP_PORT}

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD java -cp /app/gerenciadoraulas.jar org.springframework.boot.loader.PropertiesLauncher \
    -Dspring.boot.application.launch=false -Dspring.boot.health.endpoint=/actuator/health || exit 1

# Run as non-root user
USER appuser

# Launch the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"]
