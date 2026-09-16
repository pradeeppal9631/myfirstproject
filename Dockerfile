FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

RUN mkdir -p /app/kafka

RUN cp src/main/resources/kafka/aiven-kafka-truststore.jks /app/kafka/aiven-kafka-truststore.jks

CMD ["java", "-jar", "target/myfirstproject-0.0.1-SNAPSHOT.jar"]