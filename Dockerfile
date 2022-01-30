FROM openjdk:11-jre-slim
ADD yamls yamls
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} apidiff.jar
ENTRYPOINT ["java","-jar","/apidiff.jar"]