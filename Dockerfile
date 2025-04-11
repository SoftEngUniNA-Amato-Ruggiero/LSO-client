FROM docker.io/eclipse-temurin:11 AS build
COPY . /usr/src/lso-client
WORKDIR /usr/src/lso-client
RUN chmod +x gradlew && \
    ./gradlew clean shadowJar

    
FROM docker.io/eclipse-temurin:11-jre
COPY --from=build /usr/src/lso-client/build/libs/lso-client-all.skill /usr/app/lso-client-all.skill
ENV ROBOT_ADDRESS=127.0.0.1
ENV SERVER_ADDRESS=127.0.0.1
WORKDIR /usr/app
ENTRYPOINT ["sh", "-c", "java -Dfurhatos.skills.brokeraddress=$ROBOT_ADDRESS -jar lso-client-all.skill $SERVER_ADDRESS"]
# Try this if skill crashes due to "Error:Could not find or load main class"
# ENTRYPOINT ["java", "-cp", "lso-client-all.skill", "furhatos.app.clientlso.ClientlsoSkill"]