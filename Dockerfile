FROM maven

WORKDIR /app

COPY ./target/cargoflow-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
