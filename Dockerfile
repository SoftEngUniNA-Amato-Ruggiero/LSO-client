FROM docker.io/eclipse-temurin:8

COPY . /usr/src/lso-client
WORKDIR /usr/src/lso-client

RUN chmod +x gradlew && ./gradlew clean shadowJar

ENTRYPOINT ["java", "-jar", "build/libs/lso-client-all.skill"]
# Try this if skill crashes due to "Error:Could not find or load main class"
# ENTRYPOINT ["java", "-cp", "build/libs/LSO-client-all.skill", "furhatos.app.clientlso.ClientlsoSkill"]

CMD ["-Dfurhatos.skills.brokeraddress=host.docker.internal"]