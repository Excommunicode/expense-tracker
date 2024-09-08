FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar expense-tracker.jar
ENTRYPOINT ["java","-jar","/expense-tracker.jar"]