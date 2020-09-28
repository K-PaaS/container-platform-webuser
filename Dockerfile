FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} paas-ta-container-platform-webuser.war
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","-Dspring.config.location=/var/lib/jenkins/workspace/paas-ta-container-platform-webuser.deploy/src/main/resources/application.yml", "/paas-ta-container-platform-webuser.war"]
