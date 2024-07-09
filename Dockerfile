#build file jar before run Dockerfile
FROM openjdk:17

ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} api-service.jar

ENTRYPOINT ["java","-jar","api-service.jar"]

EXPOSE 8080

# docker build -t api-thienan .
#docker run -it -p 8080:8080 --name=api-container api-thienan

