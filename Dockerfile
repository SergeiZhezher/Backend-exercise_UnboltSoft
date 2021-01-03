FROM openjdk:11
COPY target/unboltsoft-0.0.1-SNAPSHOT.jar /unboltsoft.jar
EXPOSE 8080
CMD ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb_container:27017/unboltSoftDB", "-jar", "unboltsoft.jar"]