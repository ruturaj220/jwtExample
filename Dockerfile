FROM openjdk:17
EXPOSE 8080
COPY target/jwtExample.jar jwtExample.jar
ENTRYPOINT ["java", "-jar", "/jwtExample.jar"]
