FROM docker.io/eclipse-temurin:8 AS build
COPY . /usr/src/lso-client
WORKDIR /usr/src/lso-client
RUN chmod +x gradlew && \
    ./gradlew clean shadowJar

    
FROM docker.io/eclipse-temurin:11-jre
COPY --from=build /usr/src/lso-client/build/libs/lso-client-all.skill /usr/app/lso-client-all.skill
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "lso-client-all.skill"]
# Try this if skill crashes due to "Error:Could not find or load main class"
# ENTRYPOINT ["java", "-cp", "build/libs/LSO-client-all.skill", "furhatos.app.clientlso.ClientlsoSkill"]