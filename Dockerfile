FROM openjdk:19-alpine

WORKDIR /app

COPY ./AdMetricsBot-1.jar ./
COPY ./.env ./

EXPOSE 8080

CMD ["java", "-jar", "AdMetricsBot-1.jar"]