FROM openjdk:16-alpine3.13
# ENV APP_HOME=/usr/app/
# WORKDIR $APP_HOME
# EXPOSE 8080
COPY /target/fashionstore-0.0.1-SNAPSHOT.jar fashionstore-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java","-jar","/fashionstore-0.0.1-SNAPSHOT.jar"]
