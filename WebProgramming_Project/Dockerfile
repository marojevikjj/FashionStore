FROM openjdk:11
# ENV APP_HOME=/usr/app/
# WORKDIR $APP_HOME
# EXPOSE 8080
COPY /target/fashionstore-0.0.1-SNAPSHOT.jar fashionstore-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/fashionstore-0.0.1-SNAPSHOT.jar"]

# FROM adoptopenjdk/openjdk15:ubi
# ENV APP_HOME=/usr/app/
# WORKDIR $APP_HOME
# COPY build/libs/*.jar app.jar
# EXPOSE 8080
# CMD ["java", "-jar", "app.jar"]