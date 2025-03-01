FROM docker.io/eclipse-temurin:8-jdk

FROM docker.io/eclipse-temurin:8

ARG OPENAI_API_KEY
ENV OPENAI_API_KEY=${OPENAI_API_KEY}

# Use this with Docker Swarm for better security
# ENV OPENAI_API_KEY=/run/secrets/openai_api_key

COPY . /usr/src/lso-client
WORKDIR /usr/src/lso-client

RUN chmod +x gradlew && ./gradlew clean shadowJar

ENTRYPOINT ["java", "-jar", "build/libs/lso-client-all.skill"]
# Try this if skill crashes due to "Error:Could not find or load main class"
# ENTRYPOINT ["java", "-cp", "build/libs/ClientLSO-all.skill", "furhatos.skills.Skill"]

CMD ["-Dfurhatos.skills.brokeraddress=host.docker.internal"]