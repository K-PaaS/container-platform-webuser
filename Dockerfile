FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} paas-ta-container-platform-webuser.war
ENTRYPOINT ["java","-jar","/paas-ta-container-platform-webuser.war"]
